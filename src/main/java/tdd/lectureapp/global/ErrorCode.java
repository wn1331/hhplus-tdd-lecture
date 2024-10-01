package tdd.lectureapp.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    LECTURE_NOT_EXIST("E100", HttpStatus.BAD_REQUEST,"특강이 존재하지 않습니다."),
    LECTURE_ALREADY_APPLIED("E101", HttpStatus.BAD_REQUEST, "이미 특강을 신청한 유저입니다."),
    LECTURE_NOT_ENOUGH("E102", HttpStatus.BAD_REQUEST, "특강 수강 인원을 초과했습니다(30).");

    private final String code;
    private final HttpStatus statusCode;
    private final String message;

}
