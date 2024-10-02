package tdd.lectureapp.domain.enrollment;


import java.util.List;
import java.util.Optional;
import tdd.lectureapp.infra.enrollment.Enrollment;

public interface EnrollmentRepository{

    Enrollment save(Enrollment enrollment);

    List<Enrollment> findAllByUserId(Long userId);

    Optional<Enrollment> findByUserIdAndLectureId(Long userId, Long lectureId);
}
