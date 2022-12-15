package ua.com.foxminded.sqljdbcschool.menu.consoleuseractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqljdbcschool.dao.jdbc_template.JDBCTemplateCourseDao;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

class SearchStudentsInCourseTest {

    @Mock
    JDBCTemplateCourseDao courseDaoMock;

    SearchStudentsInCourse searchStudentsInCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchStudentsInCourse = new SearchStudentsInCourse(courseDaoMock);
    }


    @Test
    void execute_searchStudentsInCourse_inputIsCourseName() {
        String inputString = "Music";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Mockito.when(courseDaoMock.searchStudentsInCourse(inputString)).thenReturn(new ArrayList<>() {{
            add(1);
        }});
        searchStudentsInCourse.execute();
        Mockito.verify(courseDaoMock, Mockito.times(1))
                .searchStudentsInCourse(inputString);
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsEmpty() {
        String inputString = "";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> searchStudentsInCourse.execute());
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsBlank() {
        String inputString = "  ";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> searchStudentsInCourse.execute());
    }

}