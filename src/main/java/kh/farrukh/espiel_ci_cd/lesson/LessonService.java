package kh.farrukh.espiel_ci_cd.lesson;

import java.util.List;

public interface LessonService {

    List<LessonResponseDTO> getLessons(Long courseId);

    LessonResponseDTO getLessonById(long id);

    LessonResponseDTO addLesson(LessonRequestDTO lessonDTO);

    LessonResponseDTO updateLesson(long id, LessonRequestDTO lessonDTO);

    void deleteLessonById(long id);
}
