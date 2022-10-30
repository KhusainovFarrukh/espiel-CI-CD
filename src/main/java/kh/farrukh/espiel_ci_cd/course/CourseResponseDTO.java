package kh.farrukh.espiel_ci_cd.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {

    private String name;
    private String description;
}
