package tdd.lectureapp.interfaces.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import tdd.lectureapp.application.lecture.LectureDetailResult;

public record AvailableLectureDto() {

    @Builder
    public record Response(
        String lecturer,
        List<LecturerLectureDto> lectureDetails
    ) {

        // LectureDetailResult 리스트를 받아 강사별로 그룹화하여 Response 리스트로 변환
        public static List<Response> fromResult(List<LectureDetailResult> results) {
            Map<String, List<LectureDetailResult>> groupedByLecturer = results.stream()
                .collect(Collectors.groupingBy(LectureDetailResult::lecturer));

            return groupedByLecturer.entrySet().stream()
                .map(entry -> {
                    String lecturer = entry.getKey();
                    List<LecturerLectureDto> lectureDetails = entry.getValue().stream()
                        .map(result -> LecturerLectureDto.builder()
                            .id(result.id())
                            .lectureId(result.lectureId())
                            .lectureDate(result.lectureDate())
                            .capacity(result.capacity())
                            .build())
                        .toList();

                    return Response.builder()
                        .lecturer(lecturer)
                        .lectureDetails(lectureDetails)
                        .build();
                })
                .toList();
        }

        @Builder
        public record LecturerLectureDto(
            Long id,
            Long lectureId,
            LocalDate lectureDate,
            Long capacity
        ) {

        }
    }
}
