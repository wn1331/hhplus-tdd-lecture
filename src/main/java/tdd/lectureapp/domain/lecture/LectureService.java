package tdd.lectureapp.domain.lecture;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.lectureapp.application.lecture.LectureCriteria;
import tdd.lectureapp.infra.enrollment.Enrollment;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;
import tdd.lectureapp.domain.enrollment.EnrollmentRepository;
import tdd.lectureapp.infra.lecture.Lecture;
import tdd.lectureapp.interfaces.api.dto.LectureDto;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public Lecture findLecture(LectureCommand command){
        // 특강 조회
        Lecture lecture = lectureRepository.findById(command.lectureId())
            .orElseThrow(() -> new CustomGlobalException(
                ErrorCode.LECTURE_NOT_EXIST));
        return lecture;
    }


    // 특강 신청
    @Transactional
    public LectureDto.Response apply(Long userId, LectureDto.Request request){

        // 특강 조회
//        Lecture lecture = lectureRepository.findById(request.lectureId())
//            .orElseThrow(() -> new CustomGlobalException(
//                ErrorCode.LECTURE_NOT_EXIST));
//        // 특강id를 이미 신청한 유저id인지 조회(등록테이블에서 조회)
//
//
//        // 수강가능인원에서 1개 제거(JPA 변경감지)
//        // lecture.decreaseRemainingSeats();
//
//        // 등록테이블 insert
//        Enrollment enrollment = enrollmentRepository.save(Enrollment.builder()
//            .userId(userId)
//            .lectureEntity(lecture)
//            .build()
//        );
//
//        return from(enrollment);
        return null;
    }



}
