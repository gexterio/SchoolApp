package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.io.ByteArrayInputStream;

class AddStudentTest {


    @Mock
    StudentDao studentDaoMock;

    AddStudent addStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addStudent = new AddStudent(studentDaoMock);
    }


    @Test
    void execute_addedStudent_inputIsFirstNameAndLastName() {
        String inputString = "FirstName" + "\n" + "LastName";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        StudentDTO student = new StudentDTO.StudentBuilder("FirstName", "LastName").build();
        Mockito.doNothing().when(studentDaoMock).create(student);
        addStudent.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1)).create(student);
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsEmpty() {
        String inputString = "" + "\n" + "";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudent.execute());
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsBlank() {
        String inputString = "   " + "\n" + "   ";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> addStudent.execute());
    }
}
