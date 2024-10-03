package tdd.lectureapp.application.lecture;

import java.time.LocalDate;
import lombok.Builder;
import tdd.lectureapp.domain.lecture.LectureDetailInfo;


@Builder
public record LectureDetailResult(

    Long id,
    Long lectureId,
    String lecturer,
    LocalDate lectureDate,
    Long capacity


) {

    public static LectureDetailResult fromInfo(LectureDetailInfo info){
        return LectureDetailResult.builder()
            .id(info.id())
            .lectureId(info.lectureId())
            .lecturer(info.lecturer())
            .lectureDate(info.lectureDate())
            .capacity(info.capacity())
            .build();
    }




}
