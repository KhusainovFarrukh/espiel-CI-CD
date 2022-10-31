package kh.farrukh.espiel_ci_cd.lesson;

import kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions.ResourceNotFoundException;
import kh.farrukh.espiel_ci_cd.course.Course;
import kh.farrukh.espiel_ci_cd.course.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonServiceImpl underTest;

    @Test
    void getLessons_returnsAllLessons_ifCourseIdIsNull() {
        //given
        List<Lesson> lessons = List.of(
                new Lesson(1L, "Lesson 1", "Content 1", new Course()),
                new Lesson(2L, "Lesson 2", "Content 2", new Course())
        );
        when(lessonRepository.findAll()).thenReturn(lessons);

        //when
        List<LessonResponseDTO> actual = underTest.getLessons(null);

        //then
        assertThat(actual).hasSize(lessons.size());
        List<Long> expectedIds = lessons.stream().map(Lesson::getId).toList();
        assertThat(actual).extracting(LessonResponseDTO::getId).containsExactlyElementsOf(expectedIds);
    }

    @Test
    void getLessons_returnsLessonsOfCourse_ifCourseIdIsNotNull() {
        //given
        when(courseRepository.existsById(1L)).thenReturn(true);
        List<Lesson> lessons = List.of(
                new Lesson(1L, "Lesson 1", "Content 1", new Course()),
                new Lesson(2L, "Lesson 2", "Content 2", new Course())
        );
        when(lessonRepository.findAllByCourseId(1L)).thenReturn(lessons);

        //when
        List<LessonResponseDTO> actual = underTest.getLessons(1L);

        //then
        assertThat(actual).hasSize(lessons.size());
        List<Long> expectedIds = lessons.stream().map(Lesson::getId).toList();
        assertThat(actual).extracting(LessonResponseDTO::getId).containsExactlyElementsOf(expectedIds);
    }

    @Test
    void getLessons_throwsException_ifCourseIdIsNotNullAndCourseDoesNotExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> underTest.getLessons(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Course")
                .hasMessageContaining("1")
                .hasMessageContaining("id");
    }

    @Test
    void getLessonById_throwsException_ifLessonDoesNotExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> underTest.getLessonById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Lesson")
                .hasMessageContaining("1")
                .hasMessageContaining("id");
    }

    @Test
    void getLessonById_returnsLesson_ifLessonExists() {
        //given
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(new Lesson(1L, "Lesson 1", "Content 1", new Course())));

        //when
        LessonResponseDTO actual = underTest.getLessonById(1L);

        //then
        verify(lessonRepository).findById(1L);
        assertThat(actual).isNotNull();
    }

    @Test
    void addLesson_addsLesson_ifCourseExists() {
        //given
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course(1L, "name", "description")));
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Lesson 1", "Content 1", 1L);
        Lesson lesson = LessonMappers.toLesson(lessonRequestDTO, courseRepository);
        when(lessonRepository.save(any())).thenReturn(lesson);

        //when
        LessonResponseDTO actual = underTest.addLesson(lessonRequestDTO);

        //then
        verify(lessonRepository).save(any());
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(lessonRequestDTO.getName());
        assertThat(actual.getContent()).isEqualTo(lessonRequestDTO.getContent());
        assertThat(actual.getCourse().getId()).isEqualTo(lessonRequestDTO.getCourseId());
    }

    @Test
    void addLesson_throwsException_ifCourseDoesNotExist() {
        //given
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Lesson 1", "Content 1", 1L);

        //when
        //then
        assertThatThrownBy(() -> underTest.addLesson(lessonRequestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Course")
                .hasMessageContaining("1")
                .hasMessageContaining("id");
    }

    @Test
    void updateLesson_updatesLesson_ifLessonExists() {
        //given
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(new Lesson(1L, "Lesson 1", "Content 1", new Course())));
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Lesson 1 new", "Content 1 new", null);
        Lesson lesson = LessonMappers.toLesson(lessonRequestDTO, courseRepository);
        lesson.setCourse(new Course(1L, "name", "description"));
        when(lessonRepository.save(any())).thenReturn(lesson);

        //when
        LessonResponseDTO actual = underTest.updateLesson(1L, lessonRequestDTO);

        //then
        verify(lessonRepository).save(any());
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(lessonRequestDTO.getName());
        assertThat(actual.getContent()).isEqualTo(lessonRequestDTO.getContent());
    }

    @Test
    void updateLesson_throwsException_ifLessonDoesNotExist() {
        //given
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Lesson 1 new", "Content 1 new", null);

        //when
        //then
        assertThatThrownBy(() -> underTest.updateLesson(1L, lessonRequestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Lesson")
                .hasMessageContaining("1")
                .hasMessageContaining("id");
    }

    @Test
    void deleteLesson_deletesLesson_ifLessonExists() {
        //given
        when(lessonRepository.existsById(1L)).thenReturn(true);

        //when
        underTest.deleteLessonById(1L);

        //then
        verify(lessonRepository).deleteById(1L);
    }

    @Test
    void deleteLesson_throwsException_ifLessonDoesNotExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> underTest.deleteLessonById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Lesson")
                .hasMessageContaining("1")
                .hasMessageContaining("id");
    }
}