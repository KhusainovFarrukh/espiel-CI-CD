package kh.farrukh.espiel_ci_cd.lesson;

import kh.farrukh.espiel_ci_cd.course.CourseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponseDTO {

    private Long id;
    private String name;
    private String content;
    private CourseResponseDTO course;
}
