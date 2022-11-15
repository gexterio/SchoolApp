package ua.com.foxminded.sql_jdbc_school;

import org.dbunit.Assertion;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

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
        Assertions.assertEquals(expectedName,actualName);
    }

    @Test
    void searchBYIdShouldTrowNewExceptionWhenInputNull() {
        Assertions.assertThrows(Exception.class, () -> studentDao.searchById(null));
    }

    @Test
    void searchBYIdShouldTrowNewExceptionWhenNoDataWithInputId() {
        Assertions.assertThrows(Exception.class, () -> studentDao.searchById(99));
    }

//    @Test
//    void addStudentToGroupShouldUpdateStudentInDBWhenInputValid () throws Exception {
//       ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
//                .getResourceAsStream("afterData/addStudentToGroup_data.xml")).getTable("students");
//        StudentDTO studentDTO = new StudentDTO.StudentBuilder("FirstName", "LastName").setStudentId(1).build();
//        studentDao.create(studentDTO);
//        studentDao.addStudentToGroup(studentDTO,1);
//        ITable actualTable = getConnection().createDataSet().getTable("students");
//        Assertion.assertEqualsIgnoreCols(expectedTable,actualTable,new String[]{"student_id"});
//    }
}
