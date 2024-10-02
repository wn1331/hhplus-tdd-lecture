package tdd.lectureapp.application.lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import tdd.lectureapp.interfaces.api.dto.AvailableLectureDto;
import tdd.lectureapp.interfaces.api.dto.AvailableLectureDto.Response;

@Builder
public record LectureDetailResult(

    Long id,
    Long lectureId,
    String lecturer,
    LocalDate lectureDate,
    Long capacity


) {
    private AvailableLectureDto.Response.LecturerLectureDto toLecturerLectureDto() {
        return AvailableLectureDto.Response.LecturerLectureDto.builder()
            .id(id)
            .lectureId(lectureId)
            .lectureDate(lectureDate)
            .capacity(capacity)
            .build();
    }

    // 강사별로 그룹화된 LectureDetailResult를 AvailableLectureDto.Response로 변환하는 메서드
    public static List<Response> groupByLecturer(List<LectureDetailResult> lectureDetails) {
        // 강사별로 그룹화
        Map<String, List<LectureDetailResult>> groupedByLecturer = lectureDetails.stream()
            .collect(Collectors.groupingBy(LectureDetailResult::lecturer));

        // 그룹화된 데이터를 계층 구조로 변환 (AvailableLectureDto.Response)
        return groupedByLecturer.entrySet().stream()
            .map(entry -> AvailableLectureDto.Response.builder()
                .lecturer(entry.getKey())
                .lectureDetails(entry.getValue().stream()
                    .map(LectureDetailResult::toLecturerLectureDto)
                    .toList())
                .build())
            .toList();
    }

}
