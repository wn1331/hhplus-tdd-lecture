package tdd.lectureapp.domain.lecture;

import java.time.LocalDate;
import java.util.List;
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
        // 수강 가능 인원에서 1개 제거, JPA 변경감지(Dirty Check)
        LectureDetail lectureDetail = lectureDetailRepository.findByIdAndLectureId(command.lectureDetailId(),command.lectureId())
            .orElseThrow(() -> new CustomGlobalException(
                ErrorCode.LECTURE_NOT_EXIST));
        lectureDetail.decreaseCapacity();
    }


    @Transactional(readOnly = true)
    public List<LectureDetailInfo> getAvailableLectureDetails(){
        return lectureDetailRepository.findByCapacityGreaterThanEqualAndLectureDateGreaterThanEqual().stream().map(it->
            LectureDetailInfo.builder()
                .id(it.getId())
                .lectureId(it.getLecture().getId())
                .lecturer(it.getLecture().getLecturer())
                .lectureDate(it.getLectureDate())
                .capacity(it.getCapacity()).build()
        ).toList();
    }


}
