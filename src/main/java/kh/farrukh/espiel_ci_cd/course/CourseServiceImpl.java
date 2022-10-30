package kh.farrukh.espiel_ci_cd.course;

import kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    @Override
    public Course addCourse(CourseRequestDTO courseDto) {
        return courseRepository.save(CourseMappers.toCourse(courseDto));
    }

    @Override
    public Course updateCourse(long id, CourseRequestDTO courseDto) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        existingCourse.setName(courseDto.getName());
        existingCourse.setDescription(courseDto.getDescription());

        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourseById(long id) {
        if (!courseRepository.existsById(id)) throw new ResourceNotFoundException("Course", "id", id);
        courseRepository.deleteById(id);
    }
}
