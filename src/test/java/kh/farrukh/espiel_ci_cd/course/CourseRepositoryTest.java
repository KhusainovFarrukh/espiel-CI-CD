package kh.farrukh.espiel_ci_cd.course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository underTest;

}