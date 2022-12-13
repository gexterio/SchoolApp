package ua.com.foxminded.sql_jdbc_school.menu.console_useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sql_jdbc_school.dao.jdbc_template.JDBCTemplateCourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.jdbc_template.JDBCTemplateStudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.io.ByteArrayInputStream;

class RemoveStudentFromCourseTest {
    @Mock
    JDBCTemplateStudentDao studentDaoMock;
    @Mock
    JDBCTemplateCourseDao courseDaoMock;

    RemoveStudentFromCourse removeStudentFromCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        removeStudentFromCourse = new RemoveStudentFromCourse(studentDaoMock, courseDaoMock);
    }

    @Test
    void execute_removeStudentFromCourse_inputIsStudentIdAndCourseId() {
        String inputString = "1" + "\n" + "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        StudentDTO student = new StudentDTO.StudentBuilder("FirstName", "LastName").setStudentId(1).build();
        CourseDTO course = new CourseDTO.CourseBuilder("CourseName").setCourseId(1).build();
        Mockito.when(studentDaoMock.searchById(1)).thenReturn(student);
        Mockito.when(courseDaoMock.searchById(1)).thenReturn(course);
        Mockito.doNothing().when(studentDaoMock).deleteStudentFromCourse(student, course);
        removeStudentFromCourse.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1))
                .deleteStudentFromCourse(student, course);
    }


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