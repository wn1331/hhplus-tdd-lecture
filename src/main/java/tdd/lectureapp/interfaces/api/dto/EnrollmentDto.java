package tdd.lectureapp.interfaces.api.dto;

import lombok.Builder;

public record EnrollmentDto(

) {
    @Builder
    public record Response(
        Long userId,
        Long lectureId,
        String lectureTitle,
        String lectureDescription,
        String lecturer

    ){

    }

}
