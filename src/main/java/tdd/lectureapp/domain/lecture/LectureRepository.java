package tdd.lectureapp.domain.lecture;


import java.util.Optional;
import tdd.lectureapp.infra.lecture.Lecture;

public interface LectureRepository{

    Optional<Lecture> findById(Long id);
}
