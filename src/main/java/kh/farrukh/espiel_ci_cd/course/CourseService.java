package kh.farrukh.espiel_ci_cd.course;

import java.util.List;

public interface CourseService {

    List<Course> getCourses();

    Course getCourseById(long id);

    Course addCourse(CourseRequestDTO courseRequestDTO);

    Course updateCourse(long id, CourseRequestDTO courseRequestDTO);

    void deleteCourseById(long id);
}
