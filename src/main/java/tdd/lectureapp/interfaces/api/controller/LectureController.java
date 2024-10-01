package tdd.lectureapp.interfaces.api.controller;

import static java.util.Collections.emptyList;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.lectureapp.application.lecture.EnrollmentResult;
import tdd.lectureapp.application.lecture.LectureFacade;
import tdd.lectureapp.interfaces.api.dto.EnrollmentDto;
import tdd.lectureapp.interfaces.api.dto.LectureDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final LectureFacade lectureFacade;

    // 특강 신청 API
    @PostMapping("/apply/{userId}")// 특강 신청 API
    public ResponseEntity<LectureDto.Response> apply(
        @PathVariable(name = "userId") Long userId,
        @RequestBody LectureDto.Request request
    ) {
        return ok(lectureFacade.apply(userId, request.toCriteria()).toDto());
    }


    // 특강 선택 API(강의/날짜별 확인)
    @GetMapping// 특강 여부 조회 API
    public ResponseEntity<List<LectureDto.Response>> check() {
        return ok(emptyList());
    }

    // 특강 신청 완료 목록 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<List<EnrollmentDto.Response>> completedList(
        @PathVariable(name = "userId") Long userId) {
        return ok(lectureFacade.getAppliedList(userId).stream().map(EnrollmentResult::toDto).toList());
    }
}
