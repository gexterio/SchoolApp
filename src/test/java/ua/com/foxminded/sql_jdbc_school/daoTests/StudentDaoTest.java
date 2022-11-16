package ua.com.foxminded.sql_jdbc_school.daoTests;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.util.Map;

public class StudentDaoTest extends DataSourceDBUnit {
    StudentDao studentDao;

    @BeforeEach
    public void setUp() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/emptyDaoTest_data.xml"));
        super.setUp();
        connection = getConnection().getConnection();
        studentDao = new StudentDao(new BasicConnectionPool(props.getProperty("JDBC_URL"), props.getProperty("USER"), props.getProperty("PASSWORD")));
    }

    @Test
    void createShouldCreateNewStudentInDBWithInputData() throws Exception {
        StudentDTO dto = new StudentDTO.StudentBuilder("FirstName", "LastName").setGroupId(1).build();
        studentDao.create(dto);
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createStudentDaoTest_data.xml"))
                .getTable("students");
        ITable actualTable = getConnection().createDataSet().getTable("students");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "group_id"});
    }

    @Test
    void createShouldThrowExceptionWhenInputIsNull() {
        Assertions.assertThrows(Exception.class, () -> studentDao.create(null));
    }

    @Test
    void createShouldThrowExceptionInStudentDTOValidationWhenInputNameEmpty() {
        Assertions.assertThrows(Exception.class,
                () -> new StudentDTO.StudentBuilder("", "").build());
    }

    @Test
    void createShouldThrowExceptionInStudentDTOValidationWhenInputNameBlank() {
        Assertions.assertThrows(Exception.class,
                () -> new StudentDTO.StudentBuilder("     ", "  ").build());
    }

    @Test
    void getAllShouldReturnListWithAllStudentsFromDB() throws Exception {
        studentDao.create(new StudentDTO.StudentBuilder("FirstName", "LastName").build());
        studentDao.create(new StudentDTO.StudentBuilder("FirstName", "LastName").build());
        studentDao.create(new StudentDTO.StudentBuilder("FirstName", "LastName").build());
        int actualSize = studentDao.getAll().size();
        int expectedSize = getConnection().createDataSet().getTable("students").getRowCount();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllShouldReturnEmptyListWhenDBEmpty() {
        int actualSize = studentDao.getAll().size();
        assertEquals(0, actualSize);
    }

    @Test
    void searchByIdShouldReturnStudentDTOWhenInputValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        String actualName = studentDao.searchById(1).getFirstName();
        String expectedName = dataSet.getTable("students").getValue(0, "FIRST_NAME").toString();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void searchBYIdShouldTrowNewExceptionWhenInputNull() {
        Assertions.assertThrows(Exception.class, () -> studentDao.searchById(null));
    }

    @Test
    void searchBYIdShouldTrowNewExceptionWhenNoDataWithInputId() {
        Assertions.assertThrows(Exception.class, () -> studentDao.searchById(99));
    }

    @Test
    void addStudentToGroupShouldUpdateStudentInDBWhenInputValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createGroupDaoTest_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/addStudentToGroup_data.xml")).getTable("students");
        studentDao.create(new StudentDTO.StudentBuilder("FirstName", "LastName").build());
        ITable table = getConnection().createDataSet().getTable("students");
        StudentDTO student = new StudentDTO.StudentBuilder("FirstName", "LastName")
                .setStudentId(Integer.parseInt(table.getValue(0, "student_id").toString()))
                .build();
        studentDao.addStudentToGroup(student, 1);
        ITable actualTable = getConnection().createDataSet().getTable("students");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id"});
    }

    @Test
    void addStudentToGroupShouldThrowNewExceptionWhenInputStudentNull() {
        Assertions.assertThrows(Exception.class, () -> studentDao.addStudentToGroup(null, 1));
    }

    @Test
    void addStudentToGroupShouldThrowNewExceptionWhenInputGroupIdInvalid() {
        StudentDTO student = new StudentDTO.StudentBuilder("FirstName", "LastName").build();
        Assertions.assertThrows(Exception.class, () -> studentDao.addStudentToGroup(student, 999));
    }

    @Test
    void addStudentToCourseShouldPutNewRowInPersonalCoursesInTableWhenInputValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/addStudentToCourse_data.xml"))
                .getTable("personal_courses");
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Law").setDescription("description").setId(2).build();
        studentDao.addStudentToCourse(student, course);
        ITable actualTable = getConnection().createDataSet().getTable("personal_courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "course_id"});
    }

    @Test
    void addStudentToCourseShouldThrowNewExceptionWhenInputStudentNull() {
        CourseDTO course = new CourseDTO.CourseBuilder("Name").setId(1).setDescription("desc").build();
        Assertions.assertThrows(Exception.class, () -> studentDao.addStudentToCourse(null, course));
    }

    @Test
    void addStudentToCourseShouldThrowNewExceptionWhenInputCourseNull() {
        StudentDTO student = new StudentDTO.StudentBuilder("FN", "LN").setStudentId(1).setGroupId(1).build();
        Assertions.assertThrows(Exception.class, () -> studentDao.addStudentToCourse(student, null));
    }

    @Test
    void addStudentToCourseShouldThrowNewExceptionWhenStudentAlreadyAddedToInputCourse() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Music").setDescription("description").setId(1).build();
        Assertions.assertThrows(Exception.class, () -> studentDao.addStudentToCourse(student, course));
    }

    @Test
    void deleteStudentFromCourseShouldRemoveRowInPersonalCoursesDBWhenInputValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/deleteStudentFromCourse_data.xml"))
                .getTable("personal_courses");
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Music").setDescription("description").setId(1).build();
        studentDao.deleteStudentFromCourse(student, course);
        ITable actualTable = getConnection().createDataSet().getTable("personal_courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "course_id"});
    }

    @Test
    void deleteStudentFromCourseShouldThrowNewExceptionWhenInputStudentNull() {
        CourseDTO course = new CourseDTO.CourseBuilder("Name").setId(1).setDescription("desc").build();
        Assertions.assertThrows(Exception.class, () -> studentDao.deleteStudentFromCourse(null, course));
    }

    @Test
    void deleteStudentFromCourseShouldThrowNewExceptionWhenInputCourseNull() {
        StudentDTO student = new StudentDTO.StudentBuilder("FN", "LN").setStudentId(1).setGroupId(1).build();
        Assertions.assertThrows(Exception.class, () -> studentDao.deleteStudentFromCourse(student, null));
    }

    @Test
    void deleteStudentFromCourseShouldDoNothingWhenInputStudentAlreadyDeleted() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int expectedSize = getConnection().createDataSet().getTable("personal_courses")
                .getRowCount() - 1;
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Music").setDescription("description").setId(1).build();
        studentDao.deleteStudentFromCourse(student, course);
        studentDao.deleteStudentFromCourse(student, course);
        studentDao.deleteStudentFromCourse(student, course);
        int actualSize = getConnection().createDataSet().getTable("personal_courses").getRowCount();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteShouldRemoveStudentFromDBWhenInputValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        studentDao.delete(student);
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/deleteStudent_data.xml"))
                .getTable("students");
        ITable actualTable = getConnection().createDataSet().getTable("students");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id"});
    }

    @Test
    void deleteShouldThrowNewExceptionWhenInputNull() {
        Assertions.assertThrows(Exception.class, () -> studentDao.delete(null));
    }

    @Test
    void deleteShouldDoNothingWhenInputStudentNotFound() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int expectedSize = getConnection().createDataSet().getTable("students")
                .getRowCount() - 1;
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        studentDao.delete(student);
        studentDao.delete(student);
        studentDao.delete(student);
        int actualSize = getConnection().createDataSet().getTable("students").getRowCount();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void searchGroupsByStudentCountShouldReturnMapWithCountOfStudentsInEachGroupsWithLessOrEqualsInputCount() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable result = getConnection().createQueryTable("result",
                "SELECT group_id, Count(student_id) as cnt FROM students WHERE group_id>0 GROUP BY students.group_id HAVING COUNT" +
                        " (student_id)<=(2) ORDER BY students.group_id;");
        Map<Integer, Integer> map = studentDao.searchGroupsByStudentCount(2);
        Assertions.assertEquals(result.getRowCount(), map.size());
    }

    @Test
    void searchGroupsByStudentCountShouldThrowNewExceptionWhenInputLessThanZero() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        Assertions.assertThrows(Exception.class, () -> studentDao.searchGroupsByStudentCount(-1));
    }

    @Test
    void searchGroupsByStudentCountShouldReturnEmpyMapExceptionWhenNoDataInDB() throws Exception {
        int actualMapSize = studentDao.searchGroupsByStudentCount(10).size();
        int expectedMapSize = 0;
        Assertions.assertEquals(expectedMapSize, actualMapSize);
    }
}
