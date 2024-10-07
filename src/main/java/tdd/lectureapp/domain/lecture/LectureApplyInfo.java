package tdd.lectureapp.domain.lecture;

import lombok.Builder;
import tdd.lectureapp.infra.enrollment.Enrollment;

@Builder
public record LectureApplyInfo(
    Long userId,
    Long lectureId,
    String lectureTitle,
    String lectureDescription,
    String lecturer
) {

    public static LectureApplyInfo fromEntity(Enrollment enrollment) {
        return LectureApplyInfo.builder()
            .userId(enrollment.getUserId())
            .lectureId(enrollment.getLecture().getId())
            .lectureTitle(enrollment.getLecture().getTitle())
            .lectureDescription(enrollment.getLecture().getDescription())
            .lecturer(enrollment.getLecture().getLecturer())
            .build();
    }
}
