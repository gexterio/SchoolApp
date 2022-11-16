package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;

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
    void executeShouldWorkCorrectlyWhenInputValid() {
        String inputString = "1" + "\n" + "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        removeStudentFromCourse.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1))
                .deleteStudentFromCourse(Mockito.any(), Mockito.any());
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {
            "1" + "\n" + "", "1" + "\n" + "  ", "" + "\n" + "1",
            "   " + "\n" + "1", "" + "\n" + "", " " + "\n" + "  "})
    void executeShouldThrowExceptionWhenInputInvalid(String inputString) {
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> removeStudentFromCourse.execute());
    }

}