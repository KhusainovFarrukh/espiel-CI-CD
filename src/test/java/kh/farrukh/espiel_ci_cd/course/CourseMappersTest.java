package kh.farrukh.espiel_ci_cd.course;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CourseMappersTest {

    @Test
    void toCourse_returnsCourse_ifCourseRequestDTOIsValid() {
        //given
        CourseRequestDTO courseRequestDTO = new CourseRequestDTO("Java", "Java is a programming language");

        //when
        Course actual = CourseMappers.toCourse(courseRequestDTO);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(courseRequestDTO.getName());
        assertThat(actual.getDescription()).isEqualTo(courseRequestDTO.getDescription());
    }

    @Test
    void toCourseResponseDTO_returnsCourseResponseDTO_ifCourseIsValid() {
        //given
        Course course = new Course("Java", "Java is a programming language");

        //when
        CourseResponseDTO actual = CourseMappers.toCourseResponseDTO(course);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(course.getName());
        assertThat(actual.getDescription()).isEqualTo(course.getDescription());
    }

}