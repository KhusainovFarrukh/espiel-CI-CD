package kh.farrukh.espiel_ci_cd.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(LessonController.API_LESSONS)
@RequiredArgsConstructor
public class LessonController {

    public static final String API_LESSONS = "/api/v1/lessons";

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonResponseDTO>> getLessons(
            @RequestParam(name = "course_id", required = false) Long courseId
    ) {
        return ResponseEntity.ok(lessonService.getLessons(courseId));
    }

    @GetMapping("{id}")
    public ResponseEntity<LessonResponseDTO> getLessonById(@PathVariable long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @PostMapping
    public ResponseEntity<LessonResponseDTO> addLesson(@Valid @RequestBody LessonRequestDTO lessonDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.addLesson(lessonDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<LessonResponseDTO> updateLesson(
            @PathVariable long id,
            @Valid @RequestBody LessonRequestDTO lessonDto
    ) {
        return ResponseEntity.ok(lessonService.updateLesson(id, lessonDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable long id) {
        lessonService.deleteLessonById(id);
        return ResponseEntity.noContent().build();
    }
}
