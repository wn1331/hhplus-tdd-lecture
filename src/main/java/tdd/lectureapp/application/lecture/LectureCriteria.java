package tdd.lectureapp.application.lecture;

import tdd.lectureapp.domain.lecture.LectureCommand;

public record LectureCriteria (
    Long lectureId,
    Long lectureDetailId

){
    public LectureCommand toCommand(){
        return new LectureCommand(lectureId, lectureDetailId);
    }

}
