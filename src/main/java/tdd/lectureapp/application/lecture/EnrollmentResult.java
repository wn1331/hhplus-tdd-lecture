package tdd.lectureapp.application.lecture;

import java.time.LocalDateTime;
import lombok.Builder;
import tdd.lectureapp.interfaces.api.dto.EnrollmentDto;
import tdd.lectureapp.interfaces.api.dto.EnrollmentDto.Response;

// 특강 신청 완료 목록 조회용
@Builder
public record EnrollmentResult(
    Long userId,
    Long lectureId,
    String lectureTitle,
    String lectureDescription,
    String lecturer
) {
    public EnrollmentDto.Response toDto(){
        return Response.builder()
            .userId(userId)
            .lectureId(lectureId)
            .lectureTitle(lectureTitle)
            .lectureDescription(lectureDescription)
            .lecturer(lecturer)
            .build();
    }

}
