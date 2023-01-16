package ua.com.foxminded.sqljdbcschool.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.foxminded.sqljdbcschool.TestSpringConfig;
import ua.com.foxminded.sqljdbcschool.TestWebMvcConfig;
import ua.com.foxminded.sqljdbcschool.controllers.menu_controllers.StudentController;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateCourseDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateStudentDao;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebMvcConfig.class, TestSpringConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentControllerTest {

    @Autowired
    WebApplicationContext context;

    @Mock
    HibernateStudentDao studentDaoMock;
    @Mock
    HibernateCourseDao courseDaoMock;
    StudentController studentController;
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
        studentController = new StudentController(studentDaoMock, courseDaoMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }


    @Test
    void successPage() throws Exception {
        mockMvc.perform(get("/students/success"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu/success"));
    }

    @Test
    void newStudent() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/new"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void create() throws Exception {
        StudentDTO student = new StudentDTO.StudentBuilder("Adam", "Jones").setGroupId(1).setStudentId(1).build();
        Mockito.doNothing().when(studentDaoMock).create(Mockito.any(StudentDTO.class));
        mockMvc.perform(post("/students/").flashAttr("student", student))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(student.getStudentId().toString()));
    }

    @Test
    void index() throws Exception {
        List<StudentDTO> studentList = List.of(
                new StudentDTO.StudentBuilder("FirstName", "LastName").build(),
                new StudentDTO.StudentBuilder("SecondName", "LastName").setGroupId(1).build());
        Mockito.when(studentDaoMock.getAll()).thenReturn(studentList);
        mockMvc.perform(get("/students/"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", studentList));
    }

    @Test
    void show() throws Exception {
        StudentDTO student = new StudentDTO.StudentBuilder("Adam", "Jones").setStudentId(1).build();
        Mockito.when(studentDaoMock.searchById(student.getStudentId())).thenReturn(student);
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/show"))
                .andExpect(model().attribute("student", student));
    }

    @Test
    void studentInfoPage() throws Exception {
        mockMvc.perform(get("/students/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/search"));
    }

    @Test
    void toShow() throws Exception {
        mockMvc.perform(post("/students/toShow?studentId=1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("1"));
    }

    @Test
    void addStudentToCoursePage() throws Exception {
        mockMvc.perform(get("/students/addStudentToCourseForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/addStudentToCourseForm"));
    }

    @Test
    void addedToCoursePage() throws Exception {
        int ID = 1;
        Mockito.when(studentDaoMock.searchById(ID)).thenReturn(null);
        Mockito.when(courseDaoMock.searchById(ID)).thenReturn(null);
        Mockito.doNothing().when(studentDaoMock).addStudentToCourse(null, null);
        mockMvc.perform(post("/students/addStudentToCourseSuccess")
                        .param("studentId", String.valueOf(ID))
                        .param("courseId", String.valueOf(ID)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(String.valueOf(ID)));
    }

    @Test
    void deleteStudentPage() throws Exception {
        mockMvc.perform(get("/students/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/delete"));
    }

    @Test
    void deletedStudentPage() throws Exception {
        int ID = 1;
        Mockito.when(studentDaoMock.searchById(ID)).thenReturn(null);
        Mockito.doNothing().when(studentDaoMock).delete(null);
        mockMvc.perform(post("/students/deleteStudentSuccess")
                        .param("studentId", String.valueOf(ID)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("success"));
    }

    @Test
    void removeStudentFromCoursePage() throws Exception {
        mockMvc.perform(get("/students/removeStudentFromCourseForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/removeStudentFromCourseForm"));
    }

    @Test
    void removedStudentFromCoursePage() throws Exception {
        int ID = 1;
        Mockito.when(studentDaoMock.searchById(ID)).thenReturn(null);
        Mockito.when(courseDaoMock.searchById(ID)).thenReturn(null);
        Mockito.doNothing().when(studentDaoMock).deleteStudentFromCourse(null, null);
        mockMvc.perform(post("/students/removeStudentFromCourseSuccess")
                        .param("studentId", String.valueOf(ID))
                        .param("courseId", String.valueOf(ID)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(String.valueOf(ID)))
                .andDo(print());
    }

    @Test
    void searchGroupsPage() throws Exception {
        mockMvc.perform(get("/students/searchGroupsForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/searchGroupsForm"));
    }

    @Test
    void groupsCountPage() throws Exception {
        int count = 10;
        Map<Integer, Integer> map = Map.of(1, 1, 2, 9);
        Mockito.when(studentDaoMock.searchGroupsByStudentCount(count)).thenReturn(map);
        mockMvc.perform(get("/students/groupsCount")
                        .param("count", String.valueOf(count)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("msg", map))
                .andExpect(view().name("students/groupsCount"));
    }

    @Test
    void searchStudentsInCoursePage() throws Exception {
        mockMvc.perform(get("/students/searchStudentsInCourseForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/searchStudentsInCourseForm"));
    }

    @Test
    void studentsInCoursePage() throws Exception {
        String courseName = "Music";
        List<Integer> studentsInCourse = List.of(1, 1, 1);
        List<StudentDTO> students = List.of(
                new StudentDTO.StudentBuilder("Adam", "Jones").build(),
                new StudentDTO.StudentBuilder("Adam", "Jones").build(),
                new StudentDTO.StudentBuilder("Adam", "Jones").build()
        );
        Mockito.when(courseDaoMock.searchStudentsInCourse(courseName)).thenReturn(studentsInCourse);
        Mockito.when(studentDaoMock.searchById(1)).thenReturn(new StudentDTO.StudentBuilder("Adam", "Jones").build());
        mockMvc.perform(get("/students/studentsInCourse")
                        .param("courseName", courseName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("students/studentsInCourse"))
                .andExpect(model().attribute("studentsInCourse", students));
    }

}
