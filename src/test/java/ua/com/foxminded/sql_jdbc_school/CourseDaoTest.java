package ua.com.foxminded.sql_jdbc_school;

import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

import java.util.List;

 class CourseDaoTest extends DataSourceDBUnit {
    CourseDao courseDao;
    StudentDao studentDao;
    BasicConnectionPool connectionPool;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        connectionPool = new BasicConnectionPool(props.getProperty("JDBC_URL"), props.getProperty("USER"), props.getProperty("PASSWORD"));
        courseDao = new CourseDao(connectionPool);
        studentDao = new StudentDao(connectionPool);
    }

    @Test
    void createShouldCreateNewCourseInDBWithInputData() {
        CourseDTO dto = new CourseDTO.CourseBuilder("Logic").setId(6).setDescription("some description").build();
        courseDao.create(dto);
        List<CourseDTO> list = courseDao.getAll();
        String expected = dto.toString();
        String actual = list.get(list.size() - 1).toString();
        assertEquals(expected, actual);
    }

    @Test
     void createShouldCreateNewGroupsInDBWithInputData() {
        List<CourseDTO> all = courseDao.getAll();
        int expectedSize = 5;
        int actualSize = all.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
     void searchStudentsInCourseShouldReturnCountOfStudentInInputCourse() {
        CourseDTO courseDTO = courseDao.getAll().get(1);
        studentDao.getAll().forEach(student -> studentDao.addStudentToCourse(student, courseDTO));
        int expectedCount = 5;
        int actualCount = courseDao.searchStudentsInCourse(courseDTO.getCourseName()).size();
        assertEquals(expectedCount, actualCount);
    }

    @Test
     void searchByNameShouldReturnValidDTOWhenInputIsValid() {
        String expected = new CourseDTO.CourseBuilder("History").setId(1).setDescription("description").build().toString();
        String actual = courseDao.searchByName("History").toString();
        assertEquals(expected, actual);
    }
    @Test
     void searchByIdShouldReturnValidDTOWhenInputIsValid() {
        String expected = new CourseDTO.CourseBuilder("History").setId(1).setDescription("description").build().toString();
        String actual = courseDao.searchById(1).toString();
        assertEquals(expected, actual);
    }


 }
