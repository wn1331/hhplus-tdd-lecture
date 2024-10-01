package tdd.lectureapp.infra.lecture;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.lectureapp.domain.lecture.LectureDetailRepository;

@Repository
@RequiredArgsConstructor
public class LectureDetailRepositoryImpl implements LectureDetailRepository {
    private final LectureDetailJpaRepository repository;


    @Override
    public Optional<LectureDetail> findByIdAndLectureId(Long id, Long lectureId) {
        return repository.findByIdAndLectureId(id,lectureId);
    }

    @Override
    public List<LectureDetail> findByCapacityGreaterThanEqual() {
        return repository.findByCapacityGreaterThanEqual(1L);
    }
}
