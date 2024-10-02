package tdd.lectureapp.infra.enrollment;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment,Long> {

    List<Enrollment> findAllByUserId(Long userId);

    Optional<Enrollment> findByUserIdAndLectureId(Long userId, Long lectureId);
}
