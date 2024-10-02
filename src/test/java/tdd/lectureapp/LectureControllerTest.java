package tdd.lectureapp;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import tdd.lectureapp.application.lecture.EnrollmentResult;
import tdd.lectureapp.application.lecture.LectureCriteria;
import tdd.lectureapp.application.lecture.LectureDetailResult;
import tdd.lectureapp.application.lecture.LectureFacade;
import tdd.lectureapp.application.lecture.LectureResult;
import tdd.lectureapp.interfaces.api.controller.LectureController;
import tdd.lectureapp.interfaces.api.dto.LectureApplyDto.Request;
import tdd.lectureapp.domain.enrollment.EnrollmentInfo;

@WebMvcTest(LectureController.class)
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureFacade lectureFacade;

    @Autowired
    private ObjectMapper objectMapper; // 직/역직렬화

    private final Long USER_ID = 1L;

    @Test
    @DisplayName("[성공] 특강 신청 API - 유효한 요청")
    void applyLecture_success() throws Exception {
        // given
        Long lectureId = 1L;
        Long lectureDetailId = 2L;
        Request request = new Request(lectureId, lectureDetailId);
        EnrollmentInfo enrollmentInfo = mock(EnrollmentInfo.class);
        LectureResult lectureResult = new LectureResult(enrollmentInfo);

        LectureCriteria expectedCriteria = new LectureCriteria(lectureId, lectureDetailId);
        when(lectureFacade.apply(eq(USER_ID), eq(expectedCriteria))).thenReturn(lectureResult);

        // when, then
        mockMvc.perform(post("/lecture/apply/{userId}", USER_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.enrollmentInfo").exists())
            .andDo(print());

        // 정확한 값으로 검증 (any() 사용을 지양)
        verify(lectureFacade).apply(eq(USER_ID), eq(expectedCriteria));
    }

    @Test
    @DisplayName("[실패] 특강 신청 API - 잘못된 요청 (유효성 검사 실패)")
    void applyLecture_invalidRequest() throws Exception {
        // given
        Request invalidRequest = new Request(-1L, -1L); // Invalid request with negative IDs

        // when, then
        mockMvc.perform(post("/lecture/apply/{userId}", USER_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest()) // Expect validation error
            .andDo(print());
    }

    @Test
    @DisplayName("[성공] 특강 신청 완료 목록 조회 API - 성공")
    void getCompletedList_success() throws Exception {
        // given
        List<EnrollmentResult> enrollmentInfos = List.of(mock(EnrollmentResult.class),
            mock(EnrollmentResult.class));

        when(lectureFacade.getAppliedList(USER_ID)).thenReturn(enrollmentInfos);

        // when, then
        mockMvc.perform(get("/lecture/{userId}", USER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2)) // Expect two enrollment results
            .andDo(print());

        verify(lectureFacade).getAppliedList(USER_ID);
    }

    @Test
    @DisplayName("[성공] 특강 선택 API - 성공")
    void availableList_success() throws Exception {
        // given
        List<LectureDetailResult> lectureDetailResults = List.of(mock(LectureDetailResult.class),
            mock(LectureDetailResult.class));

        when(lectureFacade.getAvailableLectures()).thenReturn(lectureDetailResults);

        // when, then
        mockMvc.perform(get("/lecture"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
            .andDo(print());

        verify(lectureFacade).getAvailableLectures();
    }
}
