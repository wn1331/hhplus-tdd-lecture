package tdd.lectureapp.application.enrollment;

import lombok.Builder;
import tdd.lectureapp.domain.enrollment.EnrollmentInfo;

// 특강 신청 완료 목록 조회용
@Builder
public record EnrollmentResult(
    Long userId,
    Long lectureId,
    String lectureTitle,
    String lectureDescription,
    String lecturer
) {

    public static EnrollmentResult fromInfo(EnrollmentInfo enrollmentInfo){
        return EnrollmentResult.builder()
            .userId(enrollmentInfo.userId())
            .lectureId(enrollmentInfo.lectureId())
            .lectureTitle(enrollmentInfo.lectureTitle())
            .lectureDescription(enrollmentInfo.lectureDescription())
            .lecturer(enrollmentInfo.lecturer())
            .build();
    }

}
