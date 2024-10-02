package tdd.lectureapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.lectureapp.domain.enrollment.EnrollmentInfo;
import tdd.lectureapp.domain.enrollment.EnrollmentRepository;
import tdd.lectureapp.domain.enrollment.EnrollmentService;
import tdd.lectureapp.infra.enrollment.Enrollment;
import tdd.lectureapp.infra.lecture.Lecture;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("[단위 테스트] EnrollmentService")
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private final Long USER_ID = 1L;

    @Test
    @Order(1)
    @DisplayName("[성공] apply 메서드 - 한 명의 유저가 특강에 성공적으로 신청")
    void apply_success() {
        // given
        Lecture lecture = mock(Lecture.class);  // Lecture 객체를 모킹
        Enrollment enrollment = mock(Enrollment.class);  // Enrollment 객체를 모킹

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);  // save 메서드 모킹
        when(enrollment.toInfo()).thenReturn(EnrollmentInfo.builder()
            .userId(USER_ID)
            .lectureId(1L)
            .lectureTitle("비관적 락 뿌수기")
            .lectureDescription("비관적 락에 대해 자세히 공부합니다.")
            .lecturer("OO코치님")
            .build());  // toInfo() 메서드 모킹

        // when
        EnrollmentInfo result = enrollmentService.apply(USER_ID, lecture);

        // then
        assertThat(result.userId()).isEqualTo(USER_ID);
        assertThat(result.lectureTitle()).isEqualTo("비관적 락 뿌수기");
        verify(enrollmentRepository).save(any(Enrollment.class));
    }


    @Test
    @Order(2)
    @DisplayName("[성공] getAppliedList 메서드 - 유저의 신청된 특강 목록 조회")
    void getAppliedList_success() {
        // given
        Enrollment enrollment1 = mock(Enrollment.class);
        Enrollment enrollment2 = mock(Enrollment.class);

        when(enrollmentRepository.findAllByUserId(USER_ID)).thenReturn(List.of(enrollment1, enrollment2));

        when(enrollment1.toInfo()).thenReturn(EnrollmentInfo.builder()
            .userId(USER_ID)
            .lectureId(1L)
            .lectureTitle("비관적 락 뿌수기")
            .lectureDescription("비관적 락에 대해 자세히 공부합니다.")
            .lecturer("OO코치님")
            .build());

        when(enrollment2.toInfo()).thenReturn(EnrollmentInfo.builder()
            .userId(USER_ID)
            .lectureId(2L)
            .lectureTitle("낙관적 락 뿌수기")
            .lectureDescription("낙관적 락에 대해 자세히 공부합니다.")
            .lecturer("XX코치님")
            .build());

        // when
        List<EnrollmentInfo> result = enrollmentService.getAppliedList(USER_ID);

        // then
        assertThat(result).hasSize(2);

        assertThat(result.get(0).lectureTitle()).isEqualTo("비관적 락 뿌수기");
        assertThat(result.get(0).lecturer()).isEqualTo("OO코치님");

        assertThat(result.get(1).lectureTitle()).isEqualTo("낙관적 락 뿌수기");
        assertThat(result.get(1).lecturer()).isEqualTo("XX코치님");

        verify(enrollmentRepository).findAllByUserId(USER_ID);
    }

    @Test
    @Order(3)
    @DisplayName("[성공] getAppliedList 메서드 - 신청 내역이 없는 경우 빈 리스트 반환")
    void getAppliedList_success_emptyList() {
        // given
        when(enrollmentRepository.findAllByUserId(USER_ID)).thenReturn(List.of());

        // when
        List<EnrollmentInfo> result = enrollmentService.getAppliedList(USER_ID);

        // then
        assertThat(result).isEmpty();  // 신청 내역이 없는 경우 빈 리스트여야 함

        verify(enrollmentRepository).findAllByUserId(USER_ID);
    }
}
