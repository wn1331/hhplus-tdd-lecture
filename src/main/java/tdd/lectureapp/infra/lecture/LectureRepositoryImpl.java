package tdd.lectureapp.infra.lecture;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.lectureapp.domain.lecture.LectureRepository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository repository;


    @Override
    public Optional<Lecture> findById(Long id) {
        return repository.findById(id);
    }
}
