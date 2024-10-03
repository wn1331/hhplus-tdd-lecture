package tdd.lectureapp.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import tdd.lectureapp.application.enrollment.EnrollmentResult;
import tdd.lectureapp.application.lecture.LectureCriteria;
import tdd.lectureapp.application.lecture.LectureDetailResult;
import tdd.lectureapp.application.lecture.LectureFacade;
import tdd.lectureapp.application.lecture.LectureResult;
import tdd.lectureapp.interfaces.api.controller.LectureController;
import tdd.lectureapp.interfaces.api.dto.LectureApplyDto.Request;
import org.junit.jupiter.api.TestMethodOrder;



@DisplayName("[단위 테스트] LectureController")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    @DisplayName("[성공] 특강 신청 API - 유효한 요청")
    void applyLecture_success() throws Exception {
        // given
        Long lectureId = 1L;
        Long lectureDetailId = 2L;
        Request request = new Request(lectureId, lectureDetailId);

        LectureResult lectureResult = new LectureResult(
            USER_ID,
            lectureId,
            "아무개",
            "테스트설명",
            "코치A",
            LocalDate.now()
        );

        LectureCriteria expectedCriteria = new LectureCriteria(lectureId, lectureDetailId);
        when(lectureFacade.apply(eq(USER_ID), eq(expectedCriteria))).thenReturn(lectureResult);

        // when & then
        mockMvc.perform(post("/lecture/apply/{userId}", USER_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            // `jsonPath()`를 실제 반환 필드에 맞게 수정
            .andExpect(jsonPath("$.userId").value(USER_ID))
            .andExpect(jsonPath("$.lectureTitle").value("아무개"))
            .andExpect(jsonPath("$.lecturer").value("코치A"))
            .andDo(print());

        // 정확한 값으로 검증 (any() 사용을 지양)
        verify(lectureFacade).apply(eq(USER_ID), eq(expectedCriteria));
    }


    @Test
    @Order(2)
    @DisplayName("[실패] 특강 신청 API - 잘못된 요청 (유효성 검사 실패)")
    void applyLecture_invalidRequest() throws Exception {
        // given
        Request invalidRequest = new Request(-1L, -1L);

        // when, then
        mockMvc.perform(post("/lecture/apply/{userId}", USER_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @Order(3)
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
            .andExpect(jsonPath("$.length()").value(2))
            .andDo(print());

        verify(lectureFacade).getAppliedList(USER_ID);
    }

    @Test
    @Order(4)
    @DisplayName("[성공] 가능한 특강 조회(특강 선택) API - 성공")
    void availableList_success() throws Exception {
        // Given
        List<LectureDetailResult> lectureDetails = List.of(
            new LectureDetailResult(1L, 101L, "A코치님", LocalDate.of(2024, 10, 1), 30L),
            new LectureDetailResult(2L, 102L, "B코치님", LocalDate.of(2024, 10, 2), 25L)
        );

        when(lectureFacade.getAvailableLectures()).thenReturn(lectureDetails);

        // When & Then
        mockMvc.perform(get("/lecture"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].lecturer").value("A코치님"))
            .andExpect(jsonPath("$[0].lectureDetails[0].id").value(1L))
            .andExpect(jsonPath("$[1].lecturer").value("B코치님"))
            .andExpect(jsonPath("$[1].lectureDetails[0].lectureId").value(102L));
    }
}
