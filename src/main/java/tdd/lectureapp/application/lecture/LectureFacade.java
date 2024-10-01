package tdd.lectureapp.application.lecture;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.domain.enrollment.EnrollmentInfo;
import tdd.lectureapp.domain.enrollment.EnrollmentService;
import tdd.lectureapp.domain.lecture.LectureDetailService;
import tdd.lectureapp.domain.lecture.LectureService;
import tdd.lectureapp.infra.lecture.Lecture;
import tdd.lectureapp.interfaces.api.dto.LectureDto.Request;
import tdd.lectureapp.interfaces.api.dto.LectureDto.Response;

@Component
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final LectureDetailService lectureDetailService;
    private final EnrollmentService enrollmentService;

    //
    @Transactional
    public LectureResult apply(Long userId, LectureCriteria criteria){
        // 강의가 있는지 확인
        Lecture lecture = lectureService.findLecture(criteria.toCommand());
        // 강의를 이미 신청했는지 확인 [STEP4]

        // 강의세부에서 1 차감
        lectureDetailService.decreaseCapacity(criteria.toCommand());

        return enrollmentService.apply(userId,lecture.getId(),criteria.toCommand());

    }

    @Transactional(readOnly = true)
    public List<EnrollmentResult> getAppliedList(Long userId){
        return enrollmentService.getAppliedList(userId).stream().map(EnrollmentInfo::toResult).toList();
    }

}
