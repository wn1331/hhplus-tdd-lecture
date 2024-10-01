package tdd.lectureapp.interfaces.api.dto;

import java.util.List;
import tdd.lectureapp.application.lecture.LectureCriteria;

public record LectureDto() {

    public record Request(Long lectureId, Long lectureDetailId){

        public LectureCriteria toCriteria(){
            return new LectureCriteria(lectureId, lectureDetailId);
        }
    }

    public record Response(Long lectureDetailId){

    }

}
