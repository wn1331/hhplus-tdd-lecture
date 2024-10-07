package tdd.lectureapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.List;

import java.util.Optional;
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
import tdd.lectureapp.domain.lecture.LectureApplyInfo;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
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
        Lecture lecture = mock(Lecture.class);
        Enrollment enrollment = mock(Enrollment.class);

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        when(enrollment.getUserId()).thenReturn(USER_ID);
        when(enrollment.getLecture()).thenReturn(lecture);
        when(lecture.getTitle()).thenReturn("비관적 락 뿌수기");
        when(lecture.getLecturer()).thenReturn("OO코치님");

        // when
        LectureApplyInfo result = enrollmentService.apply(USER_ID, lecture);

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
        Lecture lecture1 = mock(Lecture.class);
        Lecture lecture2 = mock(Lecture.class);

        when(enrollmentRepository.findAllByUserId(USER_ID)).thenReturn(List.of(enrollment1, enrollment2));
        when(enrollment1.getLecture()).thenReturn(lecture1);
        when(enrollment2.getLecture()).thenReturn(lecture2);
        when(lecture1.getTitle()).thenReturn("비관적 락 뿌수기");
        when(lecture1.getLecturer()).thenReturn("OO코치님");
        when(lecture2.getTitle()).thenReturn("낙관적 락 뿌수기");
        when(lecture2.getLecturer()).thenReturn("XX코치님");

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

    @Test
    @Order(4)
    @DisplayName("[실패] 이미 신청된 강의에 대해 예외 발생")
    void apply_existEnrollment_exception() {
        // given
        Long lectureId = 101L;
        Lecture lecture = mock(Lecture.class);

        // Lecture 객체의 getId()가 올바른 값을 반환하도록 설정
        when(lecture.getId()).thenReturn(lectureId);

        when(enrollmentRepository.findByUserIdAndLectureId(USER_ID, lectureId))
            .thenReturn(Optional.of(mock(Enrollment.class)));  // 이미 신청한 강의가 있는 경우

        // when, then
        assertThatThrownBy(() -> enrollmentService.apply(USER_ID, lecture))
            .isInstanceOf(CustomGlobalException.class)
            .hasMessageContaining(ErrorCode.LECTURE_ALREADY_APPLIED.getMessage());

        verify(enrollmentRepository).findByUserIdAndLectureId(USER_ID, lectureId);
    }
}
