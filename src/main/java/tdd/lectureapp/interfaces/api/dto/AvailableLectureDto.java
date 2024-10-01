package tdd.lectureapp.interfaces.api.dto;

import java.time.LocalDate;
import lombok.Builder;

public record AvailableLectureDto() {

    @Builder
    public record Response(
        Long id,
        Long lectureId,
        String lecturer,
        LocalDate lectureDate,
        Long capacity

    ){

    }

}
