package kh.farrukh.espiel_ci_cd.course;

import kh.farrukh.espiel_ci_cd.common.jpa.EntityWithId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static kh.farrukh.espiel_ci_cd.common.jpa.EntityWithId.ID_GENERATOR;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = ID_GENERATOR, sequenceName = "course_id_sequence", allocationSize = 1)
public class Course extends EntityWithId {

    @Column(nullable = false)
    private String name;
    private String description;
}
