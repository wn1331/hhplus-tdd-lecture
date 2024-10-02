package tdd.lectureapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import tdd.lectureapp.domain.lecture.LectureCommand;
import tdd.lectureapp.domain.lecture.LectureRepository;
import tdd.lectureapp.domain.lecture.LectureService;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
import tdd.lectureapp.infra.lecture.Lecture;


@ExtendWith(MockitoExtension.class)
@DisplayName("[단위 테스트] LectureService")
@TestMethodOrder(OrderAnnotation.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    private final Long LECTURE_ID = 1L;
    private final Long LECTURE_DETAIL_ID = 1L;

    @Test
    @Order(1)
    @DisplayName("findLecture 메서드 - 성공적으로 특강 조회")
    void findLecture_success() {
        // given
        LectureCommand command = new LectureCommand(LECTURE_ID,LECTURE_DETAIL_ID);
        Lecture lecture = mock(Lecture.class);

        // 레포지토리 모킹
        when(lectureRepository.findById(LECTURE_ID)).thenReturn(Optional.of(lecture));

        // when
        Lecture result = lectureService.findLecture(command);

        // then
        assertThat(result).isEqualTo(lecture);
        verify(lectureRepository).findById(LECTURE_ID); // Ensure the repository method is called
    }

    @Test
    @DisplayName("[실패] findLecture 메서드 - 특강이 존재하지 않을 때 예외 발생")
    void findLecture_lectureNotExist_exception() {
        // given
        LectureCommand command = new LectureCommand(LECTURE_ID, LECTURE_DETAIL_ID);

        when(lectureRepository.findById(LECTURE_ID)).thenReturn(Optional.empty()); // No lecture found

        // when, then
        assertThatThrownBy(() -> lectureService.findLecture(command))
            .isInstanceOf(CustomGlobalException.class)
            .hasMessageContaining(ErrorCode.LECTURE_NOT_EXIST.getMessage());

        verify(lectureRepository).findById(LECTURE_ID);
    }

}
