package tdd.lectureapp.domain.lecture;

import java.time.LocalDate;
import lombok.Builder;
import tdd.lectureapp.infra.lecture.LectureDetail;

@Builder
public record LectureDetailInfo(
    Long id,
    Long lectureId,
    String lecturer,
    LocalDate lectureDate,
    Long capacity


) {

    public static LectureDetailInfo fromEntity(LectureDetail lectureDetail){
        return LectureDetailInfo.builder()
                .id(lectureDetail.getId())
                .lectureId(lectureDetail.getLecture().getId())
                .lecturer(lectureDetail.getLecture().getLecturer())
                .lectureDate(lectureDetail.getLectureDate())
                .capacity(lectureDetail.getCapacity())
                .build();
    }
}
