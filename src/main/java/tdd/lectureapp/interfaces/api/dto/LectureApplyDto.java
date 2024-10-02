package tdd.lectureapp.interfaces.api.dto;

import jakarta.validation.constraints.Positive;
import tdd.lectureapp.application.lecture.LectureCriteria;
import tdd.lectureapp.domain.enrollment.EnrollmentInfo;

public record LectureApplyDto() {

    public record Request(@Positive Long lectureId,@Positive Long lectureDetailId){

        public LectureCriteria toCriteria(){
            return new LectureCriteria(lectureId, lectureDetailId);
        }
    }

    public record Response(EnrollmentInfo enrollmentInfo){

    }

}
