package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;

class ExitTest {
    @Mock
    BasicConnectionPool connectionPool;

    Exit exit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exit = new Exit(connectionPool);
    }

    @Test
    void executeShouldWorkCorrectlyWhenInputValid() {
        exit.execute();
    }
}
