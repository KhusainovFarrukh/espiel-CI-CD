package kh.farrukh.espiel_ci_cd.course;

import java.util.List;

public interface CourseService {

    List<CourseResponseDTO> getCourses();

    CourseResponseDTO getCourseById(long id);

    CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO);

    CourseResponseDTO updateCourse(long id, CourseRequestDTO courseRequestDTO);

    void deleteCourseById(long id);
}
