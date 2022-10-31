package kh.farrukh.espiel_ci_cd.lesson;

import kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions.BadRequestException;
import kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions.ResourceNotFoundException;
import kh.farrukh.espiel_ci_cd.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<LessonResponseDTO> getLessons(Long courseId) {
        if (courseId == null) {
            return lessonRepository.findAll().stream().map(LessonMappers::toLessonResponseDTO).toList();
        } else {
            if (!courseRepository.existsById(courseId)) throw new ResourceNotFoundException("Course", "id", courseId);
            return lessonRepository.findAllByCourseId(courseId).stream().map(LessonMappers::toLessonResponseDTO).toList();
        }
    }

    @Override
    public LessonResponseDTO getLessonById(long id) {
        return LessonMappers.toLessonResponseDTO(
                lessonRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", id))
        );
    }

    @Override
    public LessonResponseDTO addLesson(LessonRequestDTO lessonDto) {
        if (lessonDto.getCourseId() == null) throw new BadRequestException("Course id");
        return LessonMappers.toLessonResponseDTO(
                lessonRepository.save(LessonMappers.toLesson(lessonDto, courseRepository))
        );
    }

    @Override
    public LessonResponseDTO updateLesson(long id, LessonRequestDTO lessonDto) {
        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", id));

        existingLesson.setName(lessonDto.getName());
        existingLesson.setContent(lessonDto.getContent());

        return LessonMappers.toLessonResponseDTO(lessonRepository.save(existingLesson));
    }

    @Override
    public void deleteLessonById(long id) {
        if (!lessonRepository.existsById(id)) throw new ResourceNotFoundException("Lesson", "id", id);
        lessonRepository.deleteById(id);
    }
}
