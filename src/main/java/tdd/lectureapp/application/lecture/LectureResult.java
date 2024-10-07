package tdd.lectureapp.application.lecture;

import java.time.LocalDate;
import lombok.Builder;
import tdd.lectureapp.domain.lecture.LectureApplyInfo;

@Builder
public record LectureResult(
    Long userId,
    Long lectureId,
    String lectureTitle,
    String lectureDescription,
    String lecturer,
    LocalDate lectureDate
) {

    public static LectureResult fromInfo(LectureApplyInfo info, LocalDate lectureDate){
        return LectureResult.builder()
            .userId(info.userId())
            .lectureId(info.lectureId())
            .lectureTitle(info.lectureTitle())
            .lectureDescription(info.lectureDescription())
            .lecturer(info.lecturer())
            .lectureDate(lectureDate)
            .build();
    }


}
