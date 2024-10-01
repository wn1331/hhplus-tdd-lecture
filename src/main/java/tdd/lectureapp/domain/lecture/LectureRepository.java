package tdd.lectureapp.domain.lecture;


import java.util.List;
import java.util.Optional;
import tdd.lectureapp.infra.lecture.Lecture;

public interface LectureRepository{

    Optional<Lecture> findById(Long id);

    List<Lecture> findAll();
}
