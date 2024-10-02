package tdd.lectureapp.interfaces.api.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

public record AvailableLectureDto() {

    @Builder
    public record Response(
        String lecturer,  // 강사 이름
        List<LecturerLectureDto> lecturerDetails

    ){
        @Builder
        public record LecturerLectureDto(
            Long id,
            Long lectureId,
            LocalDate lectureDate,
            Long capacity
        ){

        }

    }

}
