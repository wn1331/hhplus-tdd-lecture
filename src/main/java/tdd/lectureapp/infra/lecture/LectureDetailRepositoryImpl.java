package tdd.lectureapp.infra.lecture;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.lectureapp.domain.lecture.LectureDetailRepository;

@Repository
@RequiredArgsConstructor
public class LectureDetailRepositoryImpl implements LectureDetailRepository {
    private final LectureDetailJpaRepository repository;


    @Override
    public Optional<LectureDetail> findById(Long id) {
        return repository.findById(id);
    }
}
