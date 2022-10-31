package kh.farrukh.espiel_ci_cd.lesson;

import kh.farrukh.espiel_ci_cd.course.Course;
import kh.farrukh.espiel_ci_cd.course.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class LessonRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    void findAllByCourseId_returnsEmptyList_ifThereIsNoLessons() {
        //given
        //when
        List<Lesson> actual = underTest.findAllByCourseId(1L);

        //then
        assertThat(actual).isEmpty();
    }

    @Test
    void findAllByCourseId_returnsListOfLessons_ifThereAreLessonsOfThisCourse() {
        //given
        Course course = courseRepository.save(new Course(1L, "Java", "Java course"));
        List<Lesson> expected = underTest.saveAll(List.of(
                new Lesson("Java", "Java course", course),
                new Lesson("Java", "Java course", course))
        );

        //when
        List<Lesson> actual = underTest.findAllByCourseId(course.getId());

        //then
        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findAllByCourseId_returnsEmptyList_ifThereAreLessonsOfOtherCourse() {
        //given
        Course course = courseRepository.save(new Course(1L, "Java", "Java course"));
        underTest.saveAll(List.of(
                new Lesson("Java", "Java course", course),
                new Lesson("Java", "Java course", course))
        );

        //when
        List<Lesson> actual = underTest.findAllByCourseId(2L);

        //then
        assertThat(actual).isEmpty();
    }

    @Test
    void findAllByCourseId_returnsListOfLessons_ifThereAreLessonsOfMultipleCourses() {
        //given
        List<Course> courses = courseRepository.saveAll(List.of(
                new Course("Java", "Java course"),
                new Course("Python", "Python course")
        ));
        List<Lesson> expected = underTest.saveAll(List.of(
                new Lesson("Java", "Java course", courses.get(0)),
                new Lesson("Java", "Java course", courses.get(0))
        ));
        underTest.saveAll(List.of(
                new Lesson("Python", "Python course", courses.get(1)),
                new Lesson("Python", "Python course", courses.get(1))
        ));

        //when
        List<Lesson> actual = underTest.findAllByCourseId(courses.get(0).getId());

        //then
        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo(expected);
    }
}