package ua.com.foxminded.sqljdbcschool.testinteg;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.sqljdbcschool.TestSpringConfig;
import ua.com.foxminded.sqljdbcschool.TestWebMvcConfig;
import ua.com.foxminded.sqljdbcschool.controllers.menu_controllers.StudentController;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.dao_tests.DataSourceDBUnit;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebMvcConfig.class, TestSpringConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentControllerIntegrationTest extends DataSourceDBUnit {

    @Autowired
    ApplicationContext context;

    @Autowired
    StudentDao studentDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    StudentController studentController;

    private MockMvc mockMvc;

    @BeforeAll
    public void setUpBeans() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @BeforeEach
    public void setUp() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/emptyDaoTest_data.xml"));
        super.setUp();
        connection = getConnection().getConnection();
    }

    @Test
    public void create_createNewStudentInDB_inputIsValid() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createStudentDaoTest_data.xml"))
                .getTable("students");
        StudentDTO student = new StudentDTO.StudentBuilder("FirstName", "LastName").build();
        mockMvc.perform(post("/students/").flashAttr("student", student))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(student.getStudentId().toString()));
        ITable actualTable = getConnection().createDataSet().getTable("students");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "group_id"});
    }

    @Test
    public void index_putIntoModelAllStudentsFromDB() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/addStudentToCourse_data.xml"));
        super.setUp();
        ITable table = getConnection().createDataSet().getTable("students");
        List<StudentDTO> expectedStudentsList = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            int studentId = (int) table.getValue(i, "student_id");
            String firstName = (String) table.getValue(i, "first_name");
            String lastName = (String) table.getValue(i, "last_name");
            int groupId = (int) table.getValue(i, "group_id");
            expectedStudentsList.add(new StudentDTO.StudentBuilder(firstName, lastName)
                    .setStudentId((studentId)).setGroupId(groupId).build());
        }
        mockMvc.perform(get("/students/"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", expectedStudentsList));
    }

    @Test
    public void show_putIntoModelStudent_InputIsValidStudentID() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createStudentDaoTest_data.xml"));
        super.setUp();
        ITable table = getConnection().createDataSet().getTable("students");
        int studentId = (int) table.getValue(0, "student_id");
        int groupId = (int) table.getValue(0, "group_id");
        String firstName = (String) table.getValue(0, "first_name");
        String lastName = (String) table.getValue(0, "last_name");
        StudentDTO expectedStudent = new StudentDTO.StudentBuilder(firstName, lastName)
                .setStudentId(studentId).setGroupId(groupId).build();
        mockMvc.perform(get("/students/" + studentId))
                .andExpect(status().isOk())
                .andExpect(view().name("students/show"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", expectedStudent));
    }

    @Test
    public void addedToCoursesPage_addStudentToCourseInDB_inputIsValidIDs() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/addStudentToCourse_data.xml"))
                .getTable("personal_courses");
        int studentId = 1;
        int courseId = 2;
        mockMvc.perform(post("/students/addStudentToCourseSuccess")
                        .param("studentId", String.valueOf(studentId))
                        .param("courseId", String.valueOf(courseId)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(String.valueOf(studentId)));
        ITable actualTable = getConnection().createDataSet().getTable("personal_courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "course_id"});
    }

    @Test
    public void deleteStudentPage_deleteStudentFromDB_inputIsValidStudentID() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/deleteStudent_data.xml"))
                .getTable("students");
        int studentId = (int) getConnection().createDataSet().getTable("students").getValue(0, "student_id");
        mockMvc.perform(post("/students/deleteStudentSuccess")
                        .param("studentId", String.valueOf(studentId)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("success"));
        ITable actualTable = getConnection().createDataSet().getTable("students");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id"});
    }

    @Test
    public void removeStudentFromCoursePage_removeStudentFromCourseInDB_inputIsValidIDs() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/deleteStudentFromCourse_data.xml"))
                .getTable("personal_courses");
        IDataSet dataSet1 = getConnection().createDataSet();
        int studentId = (int) dataSet1.getTable("students").getValue(0, "student_id");
        int courseId = (int) dataSet1.getTable("courses").getValue(0, "course_id");
        mockMvc.perform(post("/students/removeStudentFromCourseSuccess")
                        .param("studentId", String.valueOf(studentId))
                        .param("courseId", String.valueOf(courseId)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(String.valueOf(studentId)));
        ITable actualTable = getConnection().createDataSet().getTable("personal_courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "course_id"});
    }

    @Test
    public void groupsCountPage_putIntoModelGroupsWithStudentCount_inputIsMoreThanZero() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int inputCount = 20;
        Map<Integer, Integer> groupsAndCount = studentDao.searchGroupsByStudentCount(inputCount);
        mockMvc.perform(get("/students/groupsCount")
                        .param("count", String.valueOf(inputCount)))
                .andExpect(status().isOk())
                .andExpect(view().name("students/groupsCount"))
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attribute("msg", groupsAndCount));
    }

    @Test
    public void studentsInCoursePage_putIntoModelAllStudentsInCourse_inputIsValidCourseName() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        String courseName = (String) getConnection().createDataSet().getTable("courses").getValue(0, "course_name");
        List<StudentDTO> students = List.of(
                new StudentDTO.StudentBuilder("Alex", "Loc").setStudentId(1).setGroupId(1).build(),
                new StudentDTO.StudentBuilder("Lanny", "Don").setStudentId(2).setGroupId(1).build(),
                new StudentDTO.StudentBuilder("Penny", "Wice").setStudentId(3).setGroupId(1).build()
        );
        mockMvc.perform(get("/students/studentsInCourse")
                        .param("courseName", courseName))
                .andExpect(status().isOk())
                .andExpect(view().name("students/studentsInCourse"))
                .andExpect(model().attributeExists("studentsInCourse"))
                .andExpect(model().attribute("studentsInCourse", students));
    }
}
