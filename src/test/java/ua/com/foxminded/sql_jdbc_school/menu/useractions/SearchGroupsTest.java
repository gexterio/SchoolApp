package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;

import java.io.ByteArrayInputStream;

class SearchGroupsTest {
    @Mock
    StudentDao studentDaoMock;

    SearchGroups searchGroups;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchGroups = new SearchGroups(studentDaoMock);
    }


    @Test
    void executeShouldWorkCorrectlyWhenInputValid() {
        String inputString = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        searchGroups.execute();
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {
            "", "  "})
    void executeShouldThrowExceptionWhenInputInvalid(String inputString) {
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> searchGroups.execute());
    }
}