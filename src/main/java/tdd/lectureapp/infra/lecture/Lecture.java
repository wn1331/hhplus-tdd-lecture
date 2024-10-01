package tdd.lectureapp.infra.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String lecturer;

    @Builder
    public Lecture(String title, String description, String lecturer, LocalDateTime startTime,
        LocalDateTime endTime) {
        this.title = title;
        this.description = description;
        this.lecturer = lecturer;
    }

}
