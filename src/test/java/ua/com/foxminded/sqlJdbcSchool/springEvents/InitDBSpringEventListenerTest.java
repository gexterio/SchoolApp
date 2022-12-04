package ua.com.foxminded.sqlJdbcSchool.springEvents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.event.ContextRefreshedEvent;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateDBInitializer;

class InitDBSpringEventListenerTest {
    @Mock
    JDBCTemplateDBInitializer dbInitializer;
    @Mock
    ContextRefreshedEvent event;

    InitDBSpringEventListener listener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listener = new InitDBSpringEventListener(dbInitializer);
    }

    @Test
    void onApplicationEvent_InvokeInitAllTablesMethodInDBInitalizer() {
        Mockito.doNothing().when(dbInitializer).initAllTables();
        listener.onApplicationEvent(event);
        Mockito.verify(dbInitializer, Mockito.times(1)).initAllTables();
    }
}