package tdd.lectureapp.domain.lecture;

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


    @Transactional(readOnly = true)
    public List<LectureDetailInfo> getAvailableLectureDetails(){
        return lectureDetailRepository.findByCapacityGreaterThanEqual().stream().map(it->
            LectureDetailInfo.builder()
                .id(it.getId())
                .lectureId(it.getLecture().getId())
                .lecturer(it.getLecture().getLecturer())
                .lectureDate(it.getLectureDate())
                .capacity(it.getCapacity()).build()
        ).toList();
    }


}
