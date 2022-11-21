package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;

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
    void execute_closePoolConnection() {
        exit.execute();
    }
}
