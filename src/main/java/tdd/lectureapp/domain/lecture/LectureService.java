package tdd.lectureapp.domain.lecture;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
import tdd.lectureapp.domain.enrollment.EnrollmentRepository;
import tdd.lectureapp.infra.lecture.Lecture;
import tdd.lectureapp.interfaces.api.dto.LectureApplyDto;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public Lecture findLecture(LectureCommand command){
        // 특강 조회
        return lectureRepository.findById(command.lectureId())
            .orElseThrow(() -> new CustomGlobalException(
                ErrorCode.LECTURE_NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public List<String> findAllLectures(){
        return lectureRepository.findAll().stream().map(Lecture::getLecturer).toList();
    }





}
