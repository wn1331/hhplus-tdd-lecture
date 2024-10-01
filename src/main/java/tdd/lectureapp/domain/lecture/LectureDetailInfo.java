package tdd.lectureapp.domain.lecture;

import java.time.LocalDate;
import lombok.Builder;
import tdd.lectureapp.application.lecture.LectureDetailResult;

@Builder
public record LectureDetailInfo(
    Long id,
    Long lectureId,
    String lecturer,
    LocalDate lectureDate,
    Long capacity


) {
    public LectureDetailResult toResult(){
        return LectureDetailResult.builder()
                .id(id)
                .lectureId(lectureId)
                .lecturer(lecturer)
                .lectureDate(lectureDate)
                .capacity(capacity)
                .build();
    }
}
