package tdd.lectureapp.domain.lecture;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
import tdd.lectureapp.infra.lecture.LectureDetail;

@Service
@RequiredArgsConstructor
public class LectureDetailService {

    private final LectureDetailRepository lectureDetailRepository;


    @Transactional
    public void decreaseCapacity(LectureCommand command) {
        //        // 수강가능인원에서 1개 제거(JPA 변경감지)
        LectureDetail lectureDetail = lectureDetailRepository.findById(command.lectureDetailId())
            .orElseThrow(() -> new CustomGlobalException(
                ErrorCode.LECTURE_NOT_EXIST));
        lectureDetail.decreaseRemainingSeats();
    }

}
