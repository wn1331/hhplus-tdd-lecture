package tdd.lectureapp.infra.enrollment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdd.lectureapp.domain.common.BaseTimeEntity;
import tdd.lectureapp.domain.enrollment.EnrollmentInfo;
import tdd.lectureapp.infra.lecture.Lecture;

@Entity
@Table(name = "ENROLLMENT",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "lecture_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Enrollment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    // 단방향 연관관계 설정: Enrollment(등록)에서 Lecture(특강)를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;



    @Builder
    public Enrollment(Long userId, Lecture lecture) {
        this.userId = userId;
        this.lecture = lecture;
    }

    public EnrollmentInfo toInfo() {
        return EnrollmentInfo.builder()
            .userId(userId)
            .lectureId(lecture.getId())
            .lectureTitle(lecture.getTitle())
            .lectureDescription(lecture.getDescription())
            .lecturer(lecture.getLecturer())
            .build();
    }
}
