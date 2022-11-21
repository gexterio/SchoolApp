package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;

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
    void execute_searchGroups_inputIsValid() {
        String inputString = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        searchGroups.execute();
    }

    @Test
    void execute_thrownIllegalArgumentException_inputIsEmpty() {
        String inputString = "";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> searchGroups.execute());
    }
    @Test
    void execute_thrownIllegalArgumentException_inputIsBlank() {
        String inputString = "   ";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Assertions.assertThrows(IllegalArgumentException.class, () -> searchGroups.execute());
    }
}