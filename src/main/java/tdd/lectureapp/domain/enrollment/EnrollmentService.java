package tdd.lectureapp.domain.enrollment;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.infra.enrollment.Enrollment;
import tdd.lectureapp.infra.lecture.Lecture;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public EnrollmentInfo apply(Long userId, Lecture lecture){
        Enrollment enrollment = enrollmentRepository.save(Enrollment.builder()
            .userId(userId)
            .lecture(lecture)
            .build()
        );
        return enrollment.toInfo();
    }

    @Transactional(readOnly = true)
    public List<EnrollmentInfo> getAppliedList(Long userId) {
        return enrollmentRepository.findAllByUserId(userId).stream().map(Enrollment::toInfo)
            .toList();
    }

}
