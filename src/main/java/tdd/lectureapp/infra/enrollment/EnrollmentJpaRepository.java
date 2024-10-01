package tdd.lectureapp.infra.enrollment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment,Long> {

    List<Enrollment> findAllByUserId(Long userId);

}
