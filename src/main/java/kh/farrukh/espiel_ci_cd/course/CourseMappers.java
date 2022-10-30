package kh.farrukh.espiel_ci_cd.course;

import org.springframework.beans.BeanUtils;

public class CourseMappers {

    public static Course toCourse(CourseRequestDTO courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        return course;
    }

    public static CourseResponseDTO toCourseResponseDTO(Course course) {
        CourseResponseDTO courseDto = new CourseResponseDTO();
        BeanUtils.copyProperties(course, courseDto);
        return courseDto;
    }

}
