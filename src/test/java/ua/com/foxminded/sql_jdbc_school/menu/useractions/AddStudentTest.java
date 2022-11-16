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
import ua.com.foxminded.sql_jdbc_school.menu.useractions.AddStudent;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class AddStudentTest {


    @Mock
    StudentDao studentDaoMock;

    AddStudent addStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addStudent = new AddStudent(studentDaoMock);
    }


    @Test
    void executeShouldWorkCorrectlyWhenInputValid() {
        String inputString = "FirstName" + "\n" + "LastName";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        addStudent.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "FirstName" + "\n" + "", "FirstName" + "\n" + "  ", "" + "\n" + "LastName",
            "   " + "\n" + "Lastname", "" + "\n" + "", " " + "\n" + "  "})
    void executeShouldThrowExceptionWhenInputInvalid(String inputString) {
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> addStudent.execute());
    }
}
