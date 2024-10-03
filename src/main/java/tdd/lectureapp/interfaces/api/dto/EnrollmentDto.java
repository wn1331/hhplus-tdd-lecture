package tdd.lectureapp.interfaces.api.dto;

import lombok.Builder;
import tdd.lectureapp.application.enrollment.EnrollmentResult;

public record EnrollmentDto(

) {

    @Builder
    public record Response(
        Long userId,
        Long lectureId,
        String lectureTitle,
        String lectureDescription,
        String lecturer

    ) {

        public static Response fromResult(EnrollmentResult enrollmentResult) {
            return Response.builder()
                .userId(enrollmentResult.userId())
                .lectureId(enrollmentResult.lectureId())
                .lectureTitle(enrollmentResult.lectureTitle())
                .lectureDescription(enrollmentResult.lectureDescription())
                .lecturer(enrollmentResult.lecturer())
                .build();
        }

    }

}
