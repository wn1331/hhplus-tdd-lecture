package tdd.lectureapp.infra.lecture;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface LectureDetailJpaRepository extends JpaRepository<LectureDetail,Long> {

    //[STEP3] 동시성 대응 PESSIMISTICLOCK
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureDetail> findByIdAndLectureId(Long id, Long lectureId);

    List<LectureDetail> findByCapacityGreaterThanEqual(Long capacity);
}
