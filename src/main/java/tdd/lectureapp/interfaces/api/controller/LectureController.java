package tdd.lectureapp.interfaces.api.controller;

import static org.springframework.http.ResponseEntity.ok;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.lectureapp.application.lecture.LectureDetailResult;
import tdd.lectureapp.application.lecture.LectureFacade;
import tdd.lectureapp.application.lecture.LectureResult;
import tdd.lectureapp.interfaces.api.dto.AvailableLectureDto;
import tdd.lectureapp.interfaces.api.dto.EnrollmentDto;
import tdd.lectureapp.interfaces.api.dto.EnrollmentDto.Response;
import tdd.lectureapp.interfaces.api.dto.LectureApplyDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final LectureFacade lectureFacade;

    // 특강 신청 API
    @PostMapping("/apply/{userId}")// 특강 신청 API
    public ResponseEntity<LectureApplyDto.Response> apply(
        @PathVariable(name = "userId") Long userId,
        @RequestBody @Valid LectureApplyDto.Request request
    ) {
        return ok(LectureApplyDto.Response.fromResult(lectureFacade.apply(userId, request.toCriteria())));
    }


    // 특강 선택 API(강의/날짜별 확인하는 신청 가능한 특강 목록)
    @GetMapping
    public ResponseEntity<List<AvailableLectureDto.Response>> availableList() {

        return ok(AvailableLectureDto.Response.fromResult(lectureFacade.getAvailableLectures()));
    }

    // 특강 신청 완료 목록 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<List<EnrollmentDto.Response>> completedList(
        @PathVariable(name = "userId") Long userId) {
        return ok(lectureFacade.getAppliedList(userId).stream().map(Response::fromResult).toList());
    }
}
