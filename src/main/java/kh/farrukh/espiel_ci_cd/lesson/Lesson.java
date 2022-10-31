package kh.farrukh.espiel_ci_cd.lesson;

import kh.farrukh.espiel_ci_cd.common.jpa.EntityWithId;
import kh.farrukh.espiel_ci_cd.course.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static kh.farrukh.espiel_ci_cd.common.jpa.EntityWithId.ID_GENERATOR;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = ID_GENERATOR, sequenceName = "lesson_id_sequence")
public class Lesson extends EntityWithId {

    @Column(nullable = false)
    private String name;

    private String content;

    @ManyToOne
    private Course course;

    public Lesson(Long id, String name, String content, Course course) {
        super.setId(id);
        this.name = name;
        this.content = content;
        this.course = course;
    }
}
