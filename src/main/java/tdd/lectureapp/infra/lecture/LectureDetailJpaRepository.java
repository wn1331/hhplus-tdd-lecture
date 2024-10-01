package tdd.lectureapp.infra.lecture;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureDetailJpaRepository extends JpaRepository<LectureDetail,Long> {

    //[STEP3] 동시성 대응 PESSIMISTICLOCK
    Optional<LectureDetail> findByIdAndLectureId(Long id, Long lectureId);

    List<LectureDetail> findByCapacityGreaterThanEqual(Long capacity);
}
