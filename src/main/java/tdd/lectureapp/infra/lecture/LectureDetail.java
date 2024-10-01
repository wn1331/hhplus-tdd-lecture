package tdd.lectureapp.infra.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdd.lectureapp.domain.common.BaseTimeEntity;
import tdd.lectureapp.global.CustomGlobalException;
import tdd.lectureapp.global.ErrorCode;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LectureDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate lectureDate;

    private Long capacity = 30L; // 남은 인원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Builder
    public LectureDetail(LocalDate lectureDate) {
        this.lectureDate = lectureDate;
    }

    // 남은 인원을 차감하는 메서드. SRP

}
