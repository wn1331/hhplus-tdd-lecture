package tdd.lectureapp.application.lecture;

import tdd.lectureapp.interfaces.api.dto.LectureDto.Response;

public record LectureResult(Long id) {

    public Response toDto(){
        return new Response(id);

    }

}
