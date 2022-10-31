package kh.farrukh.espiel_ci_cd.lesson;

import kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions.ResourceNotFoundException;
import kh.farrukh.espiel_ci_cd.course.CourseMappers;
import kh.farrukh.espiel_ci_cd.course.CourseRepository;
import org.springframework.beans.BeanUtils;

public class LessonMappers {

    public static Lesson toLesson(LessonRequestDTO lessonDTO, CourseRepository courseRepository) {
        Lesson lesson = new Lesson();
        BeanUtils.copyProperties(lessonDTO, lesson);
        lesson.setCourse(courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", lessonDTO.getCourseId())));
        return lesson;
    }

    public static LessonResponseDTO toLessonResponseDTO(Lesson lesson) {
        LessonResponseDTO lessonResponseDTO = new LessonResponseDTO();
        BeanUtils.copyProperties(lesson, lessonResponseDTO);
        lessonResponseDTO.setCourse(CourseMappers.toCourseResponseDTO(lesson.getCourse()));
        return lessonResponseDTO;
    }
}
