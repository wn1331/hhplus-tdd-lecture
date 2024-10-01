package tdd.lectureapp.lecture.interfaces.api.controller;

import static java.util.Collections.emptyList;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.lectureapp.lecture.interfaces.api.dto.LectureApplyRequest;
import tdd.lectureapp.lecture.interfaces.api.dto.LectureApplyResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    @PostMapping("/apply")// 특강 신청 API
    public ResponseEntity<LectureApplyResponse> apply(@RequestBody LectureApplyRequest request) {
        return ok(new LectureApplyResponse(1L));
    }

    @GetMapping("/check")// 특강 여부 조회 API
    public ResponseEntity<List<LectureApplyResponse>> check() {
        return ok(emptyList());
    }
}
