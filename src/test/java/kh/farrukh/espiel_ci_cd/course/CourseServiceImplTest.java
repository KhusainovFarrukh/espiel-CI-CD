package kh.farrukh.espiel_ci_cd.course;

import kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void getCourses_returnsEmptyList_ifThereIsNoCourses() {
        //given
        //when
        List<CourseResponseDTO> actual = courseService.getCourses();

        //then
        verify(courseRepository).findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void getCourses_returnsListOfCourses_ifThereAreCourses() {
        //given
        when(courseRepository.findAll()).thenReturn(List.of(new Course(), new Course()));

        //when
        List<CourseResponseDTO> actual = courseService.getCourses();

        //then
        verify(courseRepository).findAll();
        assertThat(actual).isNotEmpty();
    }

    @Test
    void getCourseById_throwsException_ifCourseDoesNotExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> courseService.getCourseById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getCourseById_returnsCourse_ifCourseExists() {
        //given
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));

        //when
        CourseResponseDTO actual = courseService.getCourseById(1L);

        //then
        verify(courseRepository).findById(1L);
        assertThat(actual).isNotNull();
    }

    @Test
    void addCourse_addsCourse() {
        //given
        CourseRequestDTO courseRequestDTO = new CourseRequestDTO("name", "description");
        Course course = CourseMappers.toCourse(courseRequestDTO);
        when(courseRepository.save(any())).thenReturn(course);

        //when
        CourseResponseDTO actual = courseService.addCourse(courseRequestDTO);

        //then
        verify(courseRepository).save(any());
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(courseRequestDTO.getName());
        assertThat(actual.getDescription()).isEqualTo(courseRequestDTO.getDescription());
    }

    @Test
    void updateCourse_throwsException_ifCourseDoesNotExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> courseService.updateCourse(1L, new CourseRequestDTO()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateCourse_updatesCourse_ifCourseExists() {
        //given
        CourseRequestDTO courseRequestDTO = new CourseRequestDTO("name", "description");
        Course course = CourseMappers.toCourse(courseRequestDTO);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any())).thenReturn(course);

        //when
        CourseResponseDTO actual = courseService.updateCourse(1L, courseRequestDTO);

        //then
        verify(courseRepository).findById(1L);
        verify(courseRepository).save(any());
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(courseRequestDTO.getName());
        assertThat(actual.getDescription()).isEqualTo(courseRequestDTO.getDescription());
    }

    @Test
    void deleteCourseById_throwsException_ifCourseDoesNotExist() {
        //given
        //when
        //then
        assertThatThrownBy(() -> courseService.deleteCourseById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteCourseById_deletesCourse_ifCourseExists() {
        //given
        when(courseRepository.existsById(1L)).thenReturn(true);

        //when
        courseService.deleteCourseById(1L);

        //then
        verify(courseRepository).existsById(1L);
        verify(courseRepository).deleteById(1L);
    }
}