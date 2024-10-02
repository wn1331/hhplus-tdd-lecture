package tdd.lectureapp.infra.enrollment;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.lectureapp.domain.enrollment.EnrollmentRepository;

@Repository
@RequiredArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private final EnrollmentJpaRepository repository;


    @Override
    public Enrollment save(Enrollment enrollment) {
        return repository.save(enrollment);
    }

    @Override
    public List<Enrollment> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Optional<Enrollment> findByUserIdAndLectureId(Long userId, Long lectureId) {
        return repository.findByUserIdAndLectureId(userId, lectureId);
    }
}
