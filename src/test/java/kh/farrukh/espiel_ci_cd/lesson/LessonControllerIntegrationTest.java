package kh.farrukh.espiel_ci_cd.lesson;


import com.fasterxml.jackson.databind.ObjectMapper;
import kh.farrukh.espiel_ci_cd.course.Course;
import kh.farrukh.espiel_ci_cd.course.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static kh.farrukh.espiel_ci_cd.lesson.LessonController.API_LESSONS;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LessonControllerIntegrationTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        lessonRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    void getLessons() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));
        List<Lesson> lessons = lessonRepository.saveAll(List.of(
                new Lesson("Java 1", "Java 1 course", course),
                new Lesson("Java 2", "Java 2 course", course)
        ));

        //when
        MvcResult mvcResult = mvc.perform(get(API_LESSONS).param("course_id", course.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        List<LessonResponseDTO> lessonResponseDTOS = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, LessonResponseDTO.class)
        );
        assertThat(lessonResponseDTOS).hasSize(lessons.size());
        List<Long> expectedIds = lessons.stream().map(Lesson::getId).toList();
        assertThat(lessonResponseDTOS).extracting(LessonResponseDTO::getId).containsExactlyInAnyOrderElementsOf(expectedIds);
    }

    @Test
    void getLessonById() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));
        Lesson lesson = lessonRepository.save(new Lesson("Java 1", "Java 1 course", course));

        //when
        MvcResult mvcResult = mvc.perform(get(API_LESSONS + "/" + lesson.getId()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        LessonResponseDTO lessonResponseDTO = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                LessonResponseDTO.class
        );
        assertThat(lessonResponseDTO).isNotNull();
        assertThat(lessonResponseDTO.getId()).isEqualTo(lesson.getId());
    }

    @Test
    void addLesson() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Java 1", "Java 1 course", course.getId());

        //when
        MvcResult mvcResult = mvc.perform(post(API_LESSONS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        LessonResponseDTO lessonResponseDTO = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                LessonResponseDTO.class
        );
        assertThat(lessonResponseDTO).isNotNull();
        assertThat(lessonResponseDTO.getId()).isNotNull();
        assertThat(lessonResponseDTO.getName()).isEqualTo(lessonRequestDTO.getName());
        assertThat(lessonResponseDTO.getContent()).isEqualTo(lessonRequestDTO.getContent());
        assertThat(lessonResponseDTO.getCourse().getId()).isEqualTo(lessonRequestDTO.getCourseId());
        assertThat(lessonRepository.findById(lessonResponseDTO.getId())).isPresent();
    }

    @Test
    void updateCourse() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));
        Lesson lesson = lessonRepository.save(new Lesson("Java 1", "Java 1 course", course));
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO("Java 2", "Java 2 course", course.getId());

        //when
        MvcResult mvcResult = mvc.perform(put(API_LESSONS + "/" + lesson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        //then
        LessonResponseDTO lessonResponseDTO = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                LessonResponseDTO.class
        );
        assertThat(lessonResponseDTO).isNotNull();
        assertThat(lessonResponseDTO.getId()).isEqualTo(lesson.getId());
        assertThat(lessonResponseDTO.getName()).isEqualTo(lessonRequestDTO.getName());
        assertThat(lessonResponseDTO.getContent()).isEqualTo(lessonRequestDTO.getContent());
        assertThat(lessonResponseDTO.getCourse().getId()).isEqualTo(lessonRequestDTO.getCourseId());
    }

    @Test
    void deleteCourse() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));
        Lesson lesson = lessonRepository.save(new Lesson("Java 1", "Java 1 course", course));

        //when
        mvc.perform(delete(API_LESSONS + "/" + lesson.getId()))
                .andExpect(status().isNoContent());

        //then
        assertThat(lessonRepository.findById(lesson.getId())).isEmpty();
    }
}