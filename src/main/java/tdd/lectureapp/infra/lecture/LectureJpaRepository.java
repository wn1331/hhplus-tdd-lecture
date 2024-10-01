package tdd.lectureapp.infra.lecture;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LectureJpaRepository extends JpaRepository<Lecture,Long> {

}
