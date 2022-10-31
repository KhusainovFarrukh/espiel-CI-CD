package kh.farrukh.espiel_ci_cd.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(CourseController.API_COURSES)
@RequiredArgsConstructor
public class CourseController {

    public static final String API_COURSES = "/api/v1/courses";

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> addCourse(@Valid @RequestBody CourseRequestDTO courseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable long id,
            @Valid @RequestBody CourseRequestDTO courseDto
    ) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }
}
