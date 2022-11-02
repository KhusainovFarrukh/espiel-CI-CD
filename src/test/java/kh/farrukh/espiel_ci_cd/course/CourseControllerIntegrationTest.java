package kh.farrukh.espiel_ci_cd.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static kh.farrukh.espiel_ci_cd.course.CourseController.API_COURSES;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerIntegrationTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    void getCourses() throws Exception {
        //given
        List<Course> courses = courseRepository.saveAll(List.of(
                new Course("Java", "Java course"),
                new Course("Spring", "Spring course")
        ));

        //when
        MvcResult mvcResult = mvc.perform(get(API_COURSES))
                .andExpect(status().isOk())
                .andReturn();

        //then
        List<CourseResponseDTO> courseResponseDTOS = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, CourseResponseDTO.class)
        );
        assertThat(courseResponseDTOS).hasSize(courses.size());
        List<Long> expectedIds = courses.stream().map(Course::getId).toList();
        assertThat(courseResponseDTOS).extracting(CourseResponseDTO::getId).containsExactlyInAnyOrderElementsOf(expectedIds);
    }

    @Test
    void getCourseById() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));

        //when
        MvcResult mvcResult = mvc.perform(get(API_COURSES + "/" + course.getId()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        CourseResponseDTO courseResponseDTO = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CourseResponseDTO.class
        );
        assertThat(courseResponseDTO.getId()).isEqualTo(course.getId());
    }

    @Test
    void addCourse() throws Exception {
        //given
        CourseRequestDTO courseRequestDTO = new CourseRequestDTO("Java", "Java course");

        //when
        MvcResult mvcResult = mvc.perform(post(API_COURSES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        CourseResponseDTO courseResponseDTO = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CourseResponseDTO.class
        );
        assertThat(courseResponseDTO.getId()).isNotNull();
        assertThat(courseResponseDTO.getName()).isEqualTo(courseRequestDTO.getName());
        assertThat(courseResponseDTO.getDescription()).isEqualTo(courseRequestDTO.getDescription());
        assertThat(courseRepository.findById(courseResponseDTO.getId())).isPresent();
    }

    @Test
    void updateCourse() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));
        CourseRequestDTO courseRequestDTO = new CourseRequestDTO("Spring", "Spring course");

        //when
        MvcResult mvcResult = mvc.perform(put(API_COURSES + "/" + course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        //then
        CourseResponseDTO courseResponseDTO = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CourseResponseDTO.class
        );
        assertThat(courseResponseDTO.getId()).isEqualTo(course.getId());
        assertThat(courseResponseDTO.getName()).isEqualTo(courseRequestDTO.getName());
        assertThat(courseResponseDTO.getDescription()).isEqualTo(courseRequestDTO.getDescription());
    }

    @Test
    void deleteCourse() throws Exception {
        //given
        Course course = courseRepository.save(new Course("Java", "Java course"));

        //when
        mvc.perform(delete(API_COURSES + "/" + course.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        //then
        assertThat(courseRepository.findById(course.getId())).isEmpty();
    }

}