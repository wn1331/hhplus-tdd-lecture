package tdd.lectureapp.domain.enrollment;


import java.util.List;
import tdd.lectureapp.infra.enrollment.Enrollment;

public interface EnrollmentRepository{

    Enrollment save();

    Enrollment save(Enrollment enrollment);

    List<Enrollment> findAllByUserId(Long userId);

}
