package ua.com.foxminded.sqlJdbcSchool.menu.consoleUseractions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

class SearchGroupsTest {
    @Mock
    JDBCTemplateStudentDao studentDaoMock;

    SearchGroups searchGroups;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchGroups = new SearchGroups(studentDaoMock);
    }


    @Test
    void execute_searchGroups_inputIsStudentCount() {
        String inputString = "10";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        Mockito.when(studentDaoMock.searchGroupsByStudentCount(Integer.parseInt(inputString)))
                .thenReturn(new HashMap<>(1) {{
                    put(1, 10);
                }});
        searchGroups.execute();
        Mockito.verify(studentDaoMock, Mockito.times(1))
                .searchGroupsByStudentCount(Integer.parseInt(inputString));
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