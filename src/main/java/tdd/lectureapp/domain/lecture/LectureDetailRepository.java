package tdd.lectureapp.domain.lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import tdd.lectureapp.infra.lecture.LectureDetail;

public interface LectureDetailRepository {

    Optional<LectureDetail> findByIdAndLectureId(Long id, Long lectureId);

    List<LectureDetail> findByCapacityGreaterThanEqualAndLectureDateGreaterThanEqual();
}
