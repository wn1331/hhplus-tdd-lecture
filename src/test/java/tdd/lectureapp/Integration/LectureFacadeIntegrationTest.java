package tdd.lectureapp.Integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tdd.lectureapp.application.lecture.LectureCriteria;
import tdd.lectureapp.application.lecture.LectureFacade;
import tdd.lectureapp.application.lecture.LectureResult;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;

@SpringBootTest
@DisplayName("[통합 테스트] Facade 테스트(동시성/비관적 잠금)")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class LectureFacadeIntegrationTest {

    @Autowired
    private LectureFacade facade;

    @Test
    @Order(1)
    @DisplayName("[실패] 동시성 - 40명이 동시에 특강 신청 시 30명만 성공, 10명은 Exception 발생")
    void apply_lecture_concurrency_fail_test() throws ExecutionException, InterruptedException {

        LectureCriteria lectureCriteria = new LectureCriteria(1L, 1L);

        List<CompletableFuture<Boolean>> tasks = new ArrayList<>();
        List<Long> exceptionCount = new ArrayList<>();  // 실패한 사용자 ID 저장 리스트

        // 40명의 사용자로부터 동시에 특강 신청 (1 ~ 40번 사용자)
        for (long i = 1; i <= 40; i++) {
            long userId = i;
            tasks.add(CompletableFuture.supplyAsync(() -> {
                LectureResult result = facade.apply(userId, lectureCriteria);
                return result != null;
            }).exceptionally(ex -> {  // 예외 발생 시 실패한 유저 ID를 기록
                exceptionCount.add(userId);
                return false;  // 예외 발생 시 실패 처리
            }));
        }

        // 모든 태스크가 완료되기를 기다림
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allTasks.join();

        long successCount = 0;
        long failureCount = 0;

        for (CompletableFuture<Boolean> task : tasks) {
            if (task.get()) {
                successCount++;
            } else {
                failureCount++;
            }
        }

        // 검증: 성공한 사람 30명, 실패한 사람 10명
        assertThat(successCount).isEqualTo(30);  // 성공한 사람 30명
        assertThat(failureCount).isEqualTo(10);  // 실패한 사람 10명
        assertThat(exceptionCount).hasSize(10);  // Exception이 10번 터졌어야 함.

    }

    @Test
    @Order(2)
    @DisplayName("[실패] 동시성 - 동일한 유저 정보로 같은 특강을 5번 신청")
    void apply_lecture_sameLecture_concurrency_failure_test() throws ExecutionException, InterruptedException {

        LectureCriteria lectureCriteria = new LectureCriteria(1L, 1L);
        long userId = 1L;  // 동일한 유저 ID

        List<CompletableFuture<Boolean>> tasks = new ArrayList<>();
        List<Long> exceptionCount = new ArrayList<>();  // 실패한 신청 저장 리스트

        // 동일한 유저가 5번 신청
        for (int i = 0; i < 5; i++) {
            tasks.add(CompletableFuture.supplyAsync(() -> {
                LectureResult result = facade.apply(userId, lectureCriteria);
                return result != null;
            }).exceptionally(ex -> {  // 예외 발생 시 실패 처리
                exceptionCount.add(userId);
                return false;  // 예외 발생 시 실패 처리
            }));
        }

        // 모든 태스크가 완료되기를 기다림
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allTasks.join();

        long successCount = 0;
        long failureCount = 0;

        for (CompletableFuture<Boolean> task : tasks) {
            if (task.get()) {
                successCount++;
            } else {
                failureCount++;
            }
        }

        // 검증: 성공은 1번, 실패는 4번
        assertThat(successCount).isEqualTo(1);  // 성공한 신청은 1번
        assertThat(failureCount).isEqualTo(4);  // 실패한 신청은 4번
        assertThat(exceptionCount).hasSize(4);  // 터진 예외는 4번
    }

    @Test
    @Order(3)
    @DisplayName("[실패] 동시성x - 동일한 유저가 같은 특강을 5번 신청 시 첫 번째는 성공, 나머지는 실패")
    void apply_lecture_sameLecture_failure_test() {
        LectureCriteria lectureCriteria = new LectureCriteria(1L, 1L);
        long userId = 1L;  // 동일한 유저 ID

        // 첫 번째 신청은 성공해야 함
        LectureResult result = facade.apply(userId, lectureCriteria);
        assertThat(result).isNotNull();

        // 두 번째부터는 모두 예외가 발생해야 함
        for (int i = 0; i < 4; i++) {
            assertThatThrownBy(() -> facade.apply(userId, lectureCriteria))
                .isInstanceOf(CustomGlobalException.class)
                .hasMessageContaining(ErrorCode.LECTURE_ALREADY_APPLIED.getMessage());
        }
    }


}
