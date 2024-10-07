package tdd.lectureapp.application.lecture;


import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.application.enrollment.EnrollmentResult;
import tdd.lectureapp.domain.enrollment.EnrollmentService;
import tdd.lectureapp.domain.lecture.LectureApplyInfo;
import tdd.lectureapp.domain.lecture.LectureDetailService;
import tdd.lectureapp.domain.lecture.LectureService;
import tdd.lectureapp.infra.lecture.Lecture;


@Component
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final LectureDetailService lectureDetailService;
    private final EnrollmentService enrollmentService;


    @Transactional
    public LectureResult apply(Long userId, LectureCriteria criteria){
        // 강의가 있는지 확인
        Lecture lecture = lectureService.findLecture(criteria.toCommand());


        // 강의세부에서 1 차감 [STEP3] - 선착순 30명 이후의 신청자의 경우 실패하도록 개선
        LocalDate lectureDate = lectureDetailService.decreaseCapacity(criteria.toCommand());

        // 등록테이블에 save
        LectureApplyInfo lectureApplyInfo = enrollmentService.apply(userId, lecture);
        return LectureResult.fromInfo(lectureApplyInfo,lectureDate);

    }

    @Transactional(readOnly = true)
    public List<LectureDetailResult> getAvailableLectures(){

        return lectureDetailService.getAvailableLectureDetails().stream().map(
            LectureDetailResult::fromInfo).toList();

    }

    @Transactional(readOnly = true)
    public List<EnrollmentResult> getAppliedList(Long userId){
        return enrollmentService.getAppliedList(userId).stream().map(EnrollmentResult::fromInfo).toList();
    }

}
