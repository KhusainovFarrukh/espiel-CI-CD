package kh.farrukh.espiel_ci_cd.lesson;

import kh.farrukh.espiel_ci_cd.course.Course;
import kh.farrukh.espiel_ci_cd.course.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonMappersTest {

    @Mock
    private CourseRepository courseRepository;

    @Test
    void toLesson_returnsLesson_ifLessonRequestDTOIsValid() {
        //given
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Lesson 1", "Lesson 1 Description", 1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course(1L, "Course 1", "Course 1 Description")));

        //when
        Lesson actual = LessonMappers.toLesson(lessonRequestDTO, courseRepository);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(lessonRequestDTO.getName());
        assertThat(actual.getContent()).isEqualTo(lessonRequestDTO.getContent());
        assertThat(actual.getCourse()).isNotNull();
        assertThat(actual.getCourse().getId()).isEqualTo(lessonRequestDTO.getCourseId());
    }

    @Test
    void toLesson_returnsLesson_ifLessonRequestDTOIsValidAndCourseIdIsNull() {
        //given
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Lesson 1", "Lesson 1 Description", null);

        //when
        Lesson actual = LessonMappers.toLesson(lessonRequestDTO, courseRepository);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(lessonRequestDTO.getName());
        assertThat(actual.getContent()).isEqualTo(lessonRequestDTO.getContent());
        assertThat(actual.getCourse()).isNull();
    }

    @Test
    void toLessonResponseDTO_returnsLessonResponseDTO_ifLessonIsValid() {
        //given
        Lesson lesson = new Lesson(1L, "Lesson 1", "Lesson 1 Description", new Course(1L, "Course 1", "Course 1 Description"));

        //when
        LessonResponseDTO actual = LessonMappers.toLessonResponseDTO(lesson);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(lesson.getId());
        assertThat(actual.getName()).isEqualTo(lesson.getName());
        assertThat(actual.getContent()).isEqualTo(lesson.getContent());
        assertThat(actual.getCourse()).isNotNull();
        assertThat(actual.getCourse().getId()).isEqualTo(lesson.getCourse().getId());
    }

}