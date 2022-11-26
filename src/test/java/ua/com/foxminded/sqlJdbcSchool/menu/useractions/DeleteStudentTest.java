package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqlJdbcSchool.dao.JDBC_Template.StudentDAO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.io.ByteArrayInputStream;

class DeleteStudentTest {
    @Mock
    StudentDAO studentDaoMock;

    DeleteStudent deleteStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteStudent = new DeleteStudent(studentDaoMock);
    }


    @Test
    void execute_deletedStudentFromDB_InputIsStudentId() {
        String inputString = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        StudentDTO student = new StudentDTO.StudentBuilder("FirstName", "LastName").setStudentId(1).setGroupId(1).build();
        Mockito.when(studentDaoMock.searchById(Integer.parseInt(inputString))).thenReturn(student);
        Mockito.doNothing().when(studentDaoMock).delete(student);
        deleteStudent.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1)).delete(student);
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsEmpty() {
        String inputString = "";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deleteStudent.execute());
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsBlank() {
        String inputString = "  ";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deleteStudent.execute());
    }

    @Test
    void execute_thrownIllegalArgumentException_inputLessOrEqualsThanZero() {
        String inputString = "-1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deleteStudent.execute());
    }
}
