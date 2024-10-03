package tdd.lectureapp.domain.enrollment;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.domain.lecture.LectureApplyInfo;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
import tdd.lectureapp.infra.enrollment.Enrollment;
import tdd.lectureapp.infra.lecture.Lecture;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public LectureApplyInfo apply(Long userId, Lecture lecture) {

        // 유저가 해당 특강을 이미 신청했는지 확인 [STEP4]
        enrollmentRepository.findByUserIdAndLectureId(userId, lecture.getId())
            .ifPresent(enrollment -> {
                throw new CustomGlobalException(ErrorCode.LECTURE_ALREADY_APPLIED);
            });

        Enrollment enrollment = enrollmentRepository.save(
            Enrollment.builder().userId(userId).lecture(lecture).build());

        return LectureApplyInfo.fromEntity(enrollment);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentInfo> getAppliedList(Long userId) {
        return enrollmentRepository.findAllByUserId(userId).stream().map(EnrollmentInfo::fromEntity)
            .toList();
    }

}
