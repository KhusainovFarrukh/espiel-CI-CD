package kh.farrukh.espiel_ci_cd.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequestDTO {

    @NotEmpty
    private String name;
    private String content;
    @JsonProperty("course_id")
    private Long courseId;
}
