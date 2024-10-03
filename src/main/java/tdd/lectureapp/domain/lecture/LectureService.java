package tdd.lectureapp.domain.lecture;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
import tdd.lectureapp.infra.lecture.Lecture;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    @Transactional
    public Lecture findLecture(LectureCommand command) {
        // 특강 조회
        return lectureRepository.findById(command.lectureId())
            .orElseThrow(() -> new CustomGlobalException(
                ErrorCode.LECTURE_NOT_EXIST));
    }


}
