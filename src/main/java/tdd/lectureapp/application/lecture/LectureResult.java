package tdd.lectureapp.application.lecture;

import tdd.lectureapp.domain.enrollment.EnrollmentInfo;
import tdd.lectureapp.interfaces.api.dto.LectureApplyDto.Response;

public record LectureResult(EnrollmentInfo enrollmentInfo) {

    public Response toDto(){
        return new Response(enrollmentInfo);

    }

}
