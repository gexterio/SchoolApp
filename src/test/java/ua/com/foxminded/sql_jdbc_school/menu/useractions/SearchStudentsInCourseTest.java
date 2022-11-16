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

import java.io.ByteArrayInputStream;

class SearchStudentsInCourseTest {

    @Mock
    CourseDao courseDaoMock;

    SearchStudentsInCourse searchStudentsInCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchStudentsInCourse = new SearchStudentsInCourse(courseDaoMock);
    }


    @Test
    void executeShouldWorkCorrectlyWhenInputValid() {
        String inputString = "Music";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        searchStudentsInCourse.execute();
        Mockito.verify(courseDaoMock, Mockito.times(1))
                .searchStudentsInCourse(Mockito.any());
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {""})
    void executeShouldThrowExceptionWhenInputInvalid(String inputString) {
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> searchStudentsInCourse.execute());
    }

}