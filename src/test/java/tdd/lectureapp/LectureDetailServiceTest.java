package tdd.lectureapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tdd.lectureapp.domain.lecture.LectureCommand;
import tdd.lectureapp.domain.lecture.LectureDetailInfo;
import tdd.lectureapp.domain.lecture.LectureDetailRepository;
import tdd.lectureapp.domain.lecture.LectureDetailService;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
import tdd.lectureapp.infra.lecture.Lecture;
import tdd.lectureapp.infra.lecture.LectureDetail;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;


@ExtendWith(MockitoExtension.class)
@DisplayName("[단위 테스트] LectureDetailService")
@TestMethodOrder(OrderAnnotation.class)
class LectureDetailServiceTest {

    @Mock
    private LectureDetailRepository lectureDetailRepository;

    @InjectMocks
    private LectureDetailService lectureDetailService;

    private final Long LECTURE_ID = 1L;
    private final Long LECTURE_DETAIL_ID = 1L;

    @Test
    @Order(1)
    @DisplayName("[성공] decreaseCapacity 메서드 - 수강 가능 인원이 감소하고 정상 작동")
    void decreaseCapacity_success() {
        // given

        LectureCommand command = new LectureCommand(LECTURE_DETAIL_ID, LECTURE_ID);
        LectureDetail lectureDetail = mock(LectureDetail.class);

        when(lectureDetailRepository.findByIdAndLectureId(LECTURE_DETAIL_ID, LECTURE_ID))
            .thenReturn(Optional.of(lectureDetail));

        // when
        lectureDetailService.decreaseCapacity(command);

        // then
        verify(lectureDetail).decreaseCapacity();  // LectureDetail의 decreaseCapacity()가 호출되었는지 확인
        verify(lectureDetailRepository).findByIdAndLectureId(LECTURE_DETAIL_ID, LECTURE_ID);  // Repository 호출 확인
    }

    @Test
    @Order(2)
    @DisplayName("[실패] decreaseCapacity 메서드 - 강의 세부 정보가 없을 때 예외 발생")
    void decreaseCapacity_notExist_lectureDetail() {
        // given
        LectureCommand command = new LectureCommand(LECTURE_DETAIL_ID, LECTURE_ID);

        when(lectureDetailRepository.findByIdAndLectureId(LECTURE_DETAIL_ID, LECTURE_ID))
            .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> lectureDetailService.decreaseCapacity(command))
            .isInstanceOf(CustomGlobalException.class)
            .hasMessageContaining(ErrorCode.LECTURE_NOT_EXIST.getMessage());

        verify(lectureDetailRepository).findByIdAndLectureId(LECTURE_DETAIL_ID, LECTURE_ID);
    }

    @Test
    @Order(3)
    @DisplayName("[성공] getAvailableLectureDetails 메서드 - 수강 가능한 특강 목록 조회")
    void getAvailableLectureDetails_success() {
        // given
        LectureDetail lectureDetail1 = mock(LectureDetail.class);
        LectureDetail lectureDetail2 = mock(LectureDetail.class);
        Lecture lecture1 = mock(Lecture.class); // Lecture 객체를 모킹
        Lecture lecture2 = mock(Lecture.class);

        when(lectureDetailRepository.findByCapacityGreaterThanEqual()).thenReturn(List.of(lectureDetail1, lectureDetail2));

        when(lectureDetail1.getId()).thenReturn(1L);
        when(lectureDetail1.getLecture()).thenReturn(lecture1);
        when(lecture1.getId()).thenReturn(998L);
        when(lectureDetail1.getCapacity()).thenReturn(5L);

        when(lectureDetail2.getId()).thenReturn(2L);
        when(lectureDetail2.getLecture()).thenReturn(lecture2);
        when(lecture2.getId()).thenReturn(999L);
        when(lectureDetail2.getCapacity()).thenReturn(3L);

        // when
        List<LectureDetailInfo> result = lectureDetailService.getAvailableLectureDetails();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).lectureId()).isEqualTo(998L);
        assertThat(result.get(1).lectureId()).isEqualTo(999L);
        verify(lectureDetailRepository).findByCapacityGreaterThanEqual();
    }


}
