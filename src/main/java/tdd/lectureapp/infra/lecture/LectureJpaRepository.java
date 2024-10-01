package tdd.lectureapp.infra.lecture;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectureJpaRepository extends JpaRepository<Lecture,Long> {

}
