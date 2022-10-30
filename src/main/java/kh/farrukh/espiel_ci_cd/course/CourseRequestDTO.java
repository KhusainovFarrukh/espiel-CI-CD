package kh.farrukh.espiel_ci_cd.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDTO {

    @NotEmpty
    private String name;
    private String description;

}
