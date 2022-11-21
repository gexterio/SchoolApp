package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqlJdbcSchool.dao.CourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;

import java.io.ByteArrayInputStream;

class RemoveStudentFromCourseTest {
    @Mock
    StudentDao studentDaoMock;
    @Mock
    CourseDao courseDaoMock;

    RemoveStudentFromCourse removeStudentFromCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        removeStudentFromCourse = new RemoveStudentFromCourse(studentDaoMock, courseDaoMock);
    }

    @Test
    void execute_removeStudentFromCourse_inputIsValid() {
        String inputString = "1" + "\n" + "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        removeStudentFromCourse.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1))
                .deleteStudentFromCourse(Mockito.any(), Mockito.any());
    }


    //            "1" + "\n" + "", "1" + "\n" + "  ", "" + "\n" + "1",
//            "   " + "\n" + "1", "" + "\n" + "", " " + "\n" + "  "})
    @Test
    void execute_thrownIllegalArgumentException_inputIsEmpty() {
        String inputString = "" + "\n" + "";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> removeStudentFromCourse.execute());
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsBlank() {
        String inputString = "  " + "\n" + "    ";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> removeStudentFromCourse.execute());
    }

}