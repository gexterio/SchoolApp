package ua.com.foxminded.sql_jdbc_school.daoTests;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

class CourseDaoTest extends DataSourceDBUnit {
    CourseDao courseDao;

    @BeforeEach
    public void setUp() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/emptyDaoTest_data.xml"));
        super.setUp();
        connection = getConnection().getConnection();
        courseDao = new CourseDao(new BasicConnectionPool(props.getProperty("JDBC_URL"), props.getProperty("USER"), props.getProperty("PASSWORD")));
    }

    @Test
    void createShouldCreateNewCourseInDBWithInputData() throws Exception {
        CourseDTO dto = new CourseDTO.CourseBuilder("Music").setDescription("description").build();
        courseDao.create(dto);
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createCourseDaoTest_data.xml"))
                .getTable("courses");
        ITable actualTable = getConnection().createDataSet().getTable("courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"course_id"});
    }

    @Test
    void createShouldThrowExceptionWhenInputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseDao.create(null));
    }

    @Test
    void createShouldThrowExceptionInCourseDTOValidationWhenInputNameEmpty() {
        Assertions.assertThrows(Exception.class,
                () -> new CourseDTO.CourseBuilder("").build());
    }

    @Test
    void createShouldThrowExceptionInCourseDTOValidationWhenInputNameBlank() {
        Assertions.assertThrows(Exception.class,
                () -> new CourseDTO.CourseBuilder("     ").build());
    }

    @Test
    void getAllShouldReturnListWithAllCoursesFromDB() throws Exception {
        courseDao.create(new CourseDTO.CourseBuilder("Music").setDescription("description").build());
        courseDao.create(new CourseDTO.CourseBuilder("Law").setDescription("description").build());
        courseDao.create(new CourseDTO.CourseBuilder("Math").setDescription("description").build());
        int actualSize = courseDao.getAll().size();
        int expectedSize = getConnection().createDataSet().getTable("courses").getRowCount();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllShouldReturnEmptyListWhenDBEmpty() {
        int actualSize = courseDao.getAll().size();
        assertEquals(0, actualSize);
    }

    @Test
    void searchStudentsInCourseShouldReturnListWithStudentIdIncludedInInputCourseWhenCourseNameValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int actualSize = courseDao.searchStudentsInCourse("Music").size();
        int expectedSize = dataSet.getTable("personal_courses").getRowCount();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void searchStudentsInCourseShouldReturnEmptyListWhenNoStudentsInInputCourse() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int actualSize = courseDao.searchStudentsInCourse("Law").size();
        Assertions.assertEquals(0, actualSize);
    }

    @Test
    void searchStudentsInCourseShouldThrowNewExceptionWhenInputInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseDao.searchByName("InvalidName"));
    }

    @Test
    void searchByNameShouldReturnCourseWhenInputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createCourseDaoTest_data.xml"));
        super.setUp();
        String expectedName = dataSet.getTable("courses").getValue(0, "course_name").toString();
        String actualName = courseDao.searchByName("Music").getCourseName();
        assertEquals(expectedName, actualName);
    }

    @Test
    void searchByNameShouldThrowNewExceptionWhenDTONotFoundWithInputName() {
        Assertions.assertThrows(Exception.class, () -> courseDao.searchByName("Music"));
    }

    @Test
    void searchByIdShouldReturnCourseWhenInputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createCourseDaoTest_data.xml"));
        super.setUp();
        String expectedName = dataSet.getTable("courses").getValue(0, "course_id").toString();
        String actualName = courseDao.searchById(1).getCourseId().toString();
        assertEquals(expectedName, actualName);
    }

    @Test
    void searchByIDShouldThrowNewExceptionWhenDTONotFoundWithInputId() {
        Assertions.assertThrows(Exception.class, () -> courseDao.searchById(999));
    }

}
