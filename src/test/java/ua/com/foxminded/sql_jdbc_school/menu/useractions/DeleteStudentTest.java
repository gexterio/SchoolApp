package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.menu.useractions.DeleteStudent;

import java.io.ByteArrayInputStream;

public class DeleteStudentTest {
    @Mock
    StudentDao studentDaoMock;

    DeleteStudent deleteStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteStudent = new DeleteStudent(studentDaoMock);
    }


    @Test
    void executeShouldWorkCorrectlyWhenInputValid() {
        String inputString = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        deleteStudent.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1))
                .delete(Mockito.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void executeShouldThrowExceptionWhenInputInvalid(String inputString) {
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> deleteStudent.execute());
    }
}
