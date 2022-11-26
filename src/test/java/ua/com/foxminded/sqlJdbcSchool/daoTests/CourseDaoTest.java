package ua.com.foxminded.sqlJdbcSchool.daoTests;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sqlJdbcSchool.dao.JDBC.CourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;

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
    void create_createNewCourseInDBWithInputData_inputIsValid() throws Exception {
        CourseDTO dto = new CourseDTO.CourseBuilder("Music").setDescription("description").build();
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createCourseDaoTest_data.xml"))
                .getTable("courses");
        courseDao.create(dto);
        ITable actualTable = getConnection().createDataSet().getTable("courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"course_id"});
    }

    @Test
    void create_thrownIllegalArgumentException_inputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseDao.create(null));
    }

    @Test
    void create_thrownException_inputIsEmpty() {
        Assertions.assertThrows(Exception.class,
                () -> new CourseDTO.CourseBuilder("").build());
    }

    @Test
    void create_thrownException_inputIsBlank() {
        Assertions.assertThrows(Exception.class,
                () -> new CourseDTO.CourseBuilder("     ").build());
    }

    @Test
    void getAll_returnedListWithAllCoursesFromDB() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createCourseDaoTest_data.xml"));
        super.setUp();
        int expectedSize = getConnection().createDataSet().getTable("courses").getRowCount();
        int actualSize = courseDao.getAll().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAll_returnedEmptyList_DBIsEmpty() {
        int actualSize = courseDao.getAll().size();
        assertEquals(0, actualSize);
    }

    @Test
    void searchStudentsInCourse_returnedListWithStudentIdIncludedInInputCourse_courseNameIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int expectedSize = dataSet.getTable("personal_courses").getRowCount();
        int actualSize = courseDao.searchStudentsInCourse("Music").size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void searchStudentsInCourse_returnedEmptyList_hasNoStudentsInInputCourse() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int actualSize = courseDao.searchStudentsInCourse("Law").size();
        Assertions.assertEquals(0, actualSize);
    }

    @Test
    void searchStudentsInCourse_thrownIllegalArgumentException_inputIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseDao.searchByName("InvalidName"));
    }

    @Test
    void searchByName_returnedCourse_inputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createCourseDaoTest_data.xml"));
        super.setUp();
        String expectedName = dataSet.getTable("courses").getValue(0, "course_name").toString();
        String actualName = courseDao.searchByName("Music").getCourseName();
        assertEquals(expectedName, actualName);
    }

    @Test
    void searchByName_thrownIllegalArgumentException_courseNotFoundedWithInputName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseDao.searchByName("Music"));
    }

    @Test
    void searchById_returnedCourse_inputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createCourseDaoTest_data.xml"));
        super.setUp();
        String expectedName = dataSet.getTable("courses").getValue(0, "course_id").toString();
        String actualName = courseDao.searchById(1).getCourseId().toString();
        assertEquals(expectedName, actualName);
    }

    @Test
    void searchByID_thrownIllegalArgumentException_courseNotFoundWithInputId() {
        Assertions.assertThrows(Exception.class, () -> courseDao.searchById(999));
    }

}
