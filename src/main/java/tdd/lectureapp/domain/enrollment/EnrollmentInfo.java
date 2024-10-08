package tdd.lectureapp.domain.enrollment;

import lombok.Builder;
import tdd.lectureapp.application.enrollment.EnrollmentResult;
import tdd.lectureapp.infra.enrollment.Enrollment;

@Builder
public record EnrollmentInfo(
    Long userId,
    Long lectureId,
    String lectureTitle,
    String lectureDescription,
    String lecturer

) {

    public static EnrollmentInfo fromEntity(Enrollment enrollment){
        return EnrollmentInfo.builder()
            .userId(enrollment.getUserId())
            .lectureId(enrollment.getLecture().getId())
            .lectureTitle(enrollment.getLecture().getTitle())
            .lectureDescription(enrollment.getLecture().getDescription())
            .lecturer(enrollment.getLecture().getLecturer())
            .build();
    }


}
