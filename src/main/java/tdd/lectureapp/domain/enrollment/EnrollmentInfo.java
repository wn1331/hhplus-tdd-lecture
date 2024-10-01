package tdd.lectureapp.domain.enrollment;

import lombok.Builder;
import tdd.lectureapp.application.lecture.EnrollmentResult;

@Builder
public record EnrollmentInfo(
    Long userId,
    Long lectureId,
    String lecturer,
    String lectureTitle,
    String lectureDescription

) {
    public EnrollmentResult toResult(){
     return EnrollmentResult.builder()
         .userId(userId)
         .lectureId(lectureId)
         .lectureTitle(lectureTitle)
         .lectureDescription(lectureDescription)
         .build();
    }


}
