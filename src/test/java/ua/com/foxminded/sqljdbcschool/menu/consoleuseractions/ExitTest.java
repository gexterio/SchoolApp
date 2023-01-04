package ua.com.foxminded.sqljdbcschool.menu.consoleuseractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.sqljdbcschool.dao.connection.BasicConnectionPool;

class ExitTest {
    @Mock
    BasicConnectionPool connectionPoolMock;

    Exit exit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exit = new Exit(connectionPoolMock);
    }

    @Test
    void execute_closePoolConnection() {
        Mockito.doNothing().when(connectionPoolMock).closePoolConnection();
        exit.execute();
        Mockito.verify(connectionPoolMock, Mockito.times(1)).closePoolConnection();
    }
}
