package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;

import java.io.ByteArrayInputStream;

class DeleteStudentTest {
    @Mock
    StudentDao studentDaoMock;

    DeleteStudent deleteStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteStudent = new DeleteStudent(studentDaoMock);
    }


    @Test
    void execute_deletedStudentFromDB_InputIsValid() {
        String inputString = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        deleteStudent.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1))
                .delete(Mockito.any());
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
