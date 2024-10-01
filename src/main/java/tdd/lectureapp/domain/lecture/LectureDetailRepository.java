package tdd.lectureapp.domain.lecture;

import java.util.Optional;
import tdd.lectureapp.infra.lecture.LectureDetail;

public interface LectureDetailRepository {

    Optional<LectureDetail> findById(Long id);

}
