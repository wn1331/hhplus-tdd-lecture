package tdd.lectureapp.application.lecture;

import java.time.LocalDate;
import lombok.Builder;
import tdd.lectureapp.interfaces.api.dto.AvailableLectureDto;
import tdd.lectureapp.interfaces.api.dto.LectureApplyDto;

@Builder
public record LectureDetailResult(

    Long id,
    Long lectureId,
    String lecturer,
    LocalDate lectureDate,
    Long capacity


) {
    public AvailableLectureDto.Response toDto(){
        return AvailableLectureDto.Response.builder()
            .id(id)
            .lectureId(lectureId)
            .lecturer(lecturer)
            .lectureDate(lectureDate)
            .capacity(capacity)
            .build();

    }

}
