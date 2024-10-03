package tdd.lectureapp.interfaces.api.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import tdd.lectureapp.application.enrollment.EnrollmentResult;
import tdd.lectureapp.application.lecture.LectureCriteria;
import tdd.lectureapp.application.lecture.LectureResult;

public record LectureApplyDto() {

    public record Request(@Positive Long lectureId,@Positive Long lectureDetailId){

        public LectureCriteria toCriteria(){
            return new LectureCriteria(lectureId, lectureDetailId);
        }
    }

    @Builder
    public record Response(
        Long userId,
        Long lectureId,
        String lectureTitle,
        String lectureDescription,
        String lecturer
    ){

        public static Response fromResult(LectureResult result){
            return Response.builder()
                .userId(result.userId())
                .lectureId(result.lectureId())
                .lectureTitle(result.lectureTitle())
                .lectureDescription(result.lectureDescription())
                .lecturer(result.lecturer())
                .build();
        }

    }

}
