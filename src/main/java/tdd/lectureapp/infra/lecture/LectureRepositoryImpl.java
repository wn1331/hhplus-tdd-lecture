package tdd.lectureapp.infra.lecture;


import java.util.List;
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

    @Override
    public List<Lecture> findAll() {
        return repository.findAll();
    }
}
