package tdd.lectureapp.domain.enrollment;

import lombok.Builder;
import lombok.ToString;
import tdd.lectureapp.application.lecture.EnrollmentResult;

@Builder
public record EnrollmentInfo(
    Long userId,
    Long lectureId,
    String lectureTitle,
    String lectureDescription,
    String lecturer

) {
    public EnrollmentResult toResult(){
     return EnrollmentResult.builder()
         .userId(userId)
         .lectureId(lectureId)
         .lectureTitle(lectureTitle)
         .lectureDescription(lectureDescription)
         .lecturer(lecturer)
         .build();
    }


}
