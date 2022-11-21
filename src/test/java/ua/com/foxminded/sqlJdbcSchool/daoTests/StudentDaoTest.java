package ua.com.foxminded.sqlJdbcSchool.daoTests;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

class StudentDaoTest extends DataSourceDBUnit {
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
    void create_createNewStudentInDBWithInputData_inputIsValid() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createStudentDaoTest_data.xml"))
                .getTable("students");
        StudentDTO dto = new StudentDTO.StudentBuilder("FirstName", "LastName").setGroupId(1).build();
        studentDao.create(dto);
        ITable actualTable = getConnection().createDataSet().getTable("students");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "group_id"});
    }

    @Test
    void create_thrownIllegalArgumentException_inputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.create(null));
    }

    @Test
    void create_thrownException_inputIsEmpty() {
        Assertions.assertThrows(Exception.class,
                () -> new StudentDTO.StudentBuilder("", "").build());
    }

    @Test
    void create_throwException_inputIsBlank() {
        Assertions.assertThrows(Exception.class,
                () -> new StudentDTO.StudentBuilder("     ", "  ").build());
    }

    @Test
    void getAll_returnedListWithAllStudentsFromDB() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createCourseDaoTest_data.xml"));
        super.setUp();
        int expectedSize = getConnection().createDataSet().getTable("students").getRowCount();
        int actualSize = studentDao.getAll().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAll_returnEmptyList_dBIsEmpty() {
        int actualSize = studentDao.getAll().size();
        assertEquals(0, actualSize);
    }

    @Test
    void searchById_returnedStudent_inputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        String expectedName = dataSet.getTable("students").getValue(0, "FIRST_NAME").toString();
        String actualName = studentDao.searchById(1).getFirstName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void searchById_thrownIllegalArgumentException_inputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.searchById(null));
    }

    @Test
    void searchBYId_thrownException_hasNoStudentWithInputId() {
        Assertions.assertThrows(Exception.class, () -> studentDao.searchById(99));
    }

    @Test
    void addStudentToGroup_updateStudentInDB_inputIsValid() throws Exception {
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
    void addStudentToGroup_thrownIllegalArgumentException_inputStudentIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.addStudentToGroup(null, 1));
    }

    @Test
    void addStudentToGroup_thrownIllegalArgumentException_inputGroupIdIsInvalid() {
        StudentDTO student = new StudentDTO.StudentBuilder("FirstName", "LastName").build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.addStudentToGroup(student, 999));
    }

    @Test
    void addStudentToCourse_putNewRowInPersonalCoursesTable_inputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/addStudentToCourse_data.xml"))
                .getTable("personal_courses");
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Law").setDescription("description").setCourseId(2).build();
        studentDao.addStudentToCourse(student, course);
        ITable actualTable = getConnection().createDataSet().getTable("personal_courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "course_id"});
    }

    @Test
    void addStudentToCourse_thrownNewIllegalArgumentException_InputStudentIsNull() {
        CourseDTO course = new CourseDTO.CourseBuilder("Name").setCourseId(1).setDescription("desc").build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.addStudentToCourse(null, course));
    }

    @Test
    void addStudentToCourse_thrownIllegalArgumentException_inputCourseIsNull() {
        StudentDTO student = new StudentDTO.StudentBuilder("FN", "LN").setStudentId(1).setGroupId(1).build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.addStudentToCourse(student, null));
    }

    @Test
    void addStudentToCourse_thrownIllegalArgumentException_studentAlreadyAddedToInputCourse() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Music").setDescription("description").setCourseId(1).build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.addStudentToCourse(student, course));
    }

    @Test
    void deleteStudentFromCourse_removeRowInPersonalCoursesDB_inputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/deleteStudentFromCourse_data.xml"))
                .getTable("personal_courses");
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Music").setDescription("description").setCourseId(1).build();
        studentDao.deleteStudentFromCourse(student, course);
        ITable actualTable = getConnection().createDataSet().getTable("personal_courses");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"student_id", "course_id"});
    }

    @Test
    void deleteStudentFromCourse_thrownIllegalArgumentException_inputStudentIsNull() {
        CourseDTO course = new CourseDTO.CourseBuilder("Name").setCourseId(1).setDescription("desc").build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.deleteStudentFromCourse(null, course));
    }

    @Test
    void deleteStudentFromCourse_thrownIllegalArgumentException_InputCourseIsNull() {
        StudentDTO student = new StudentDTO.StudentBuilder("FN", "LN").setStudentId(1).setGroupId(1).build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.deleteStudentFromCourse(student, null));
    }

    @Test
    void deleteStudentFromCourse_DoNothing_inputStudentAlreadyDeleted() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        int expectedSize = getConnection().createDataSet().getTable("personal_courses")
                .getRowCount() - 1;
        StudentDTO student = new StudentDTO.StudentBuilder("Alex", "Loc").setGroupId(1).setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("Music").setDescription("description").setCourseId(1).build();
        studentDao.deleteStudentFromCourse(student, course);
        studentDao.deleteStudentFromCourse(student, course);
        studentDao.deleteStudentFromCourse(student, course);
        int actualSize = getConnection().createDataSet().getTable("personal_courses").getRowCount();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void delete_removeStudentFromDB_inputIsValid() throws Exception {
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
    void delete_thrownIllegalArgumentException_inputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.delete(null));
    }

    @Test
    void delete_doNothing_inputStudentNotFound() throws Exception {
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
    void searchGroupsByStudentCount_returnMapWithCountOfStudentsInEachGroupsWithLessOrEqualsInputCount_inputIsValid() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        ITable result = getConnection().createQueryTable("result",
                "SELECT group_id, Count(student_id) as cnt FROM students WHERE group_id>0 GROUP BY students.group_id HAVING COUNT" +
                        " (student_id)<=(2) ORDER BY students.group_id;");
        int expectedMapSize = studentDao.searchGroupsByStudentCount(2).size();
        Assertions.assertEquals(result.getRowCount(), expectedMapSize);
    }

    @Test
    void searchGroupsByStudentCount_thrownIllegalArgumentException_inputLessThanZero() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.searchGroupsByStudentCount(-1));
    }

    @Test
    void searchGroupsByStudentCount_thrownIllegalArgumentException_inputIsNull() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/coursesAndStudents_data.xml"));
        super.setUp();
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentDao.searchGroupsByStudentCount(null));
    }

    @Test
    void searchGroupsByStudentCount_returnedEmptyMap_NoDataInDB() {
        int expectedMapSize = 0;
        int actualMapSize = studentDao.searchGroupsByStudentCount(10).size();
        Assertions.assertEquals(expectedMapSize, actualMapSize);
    }
}
