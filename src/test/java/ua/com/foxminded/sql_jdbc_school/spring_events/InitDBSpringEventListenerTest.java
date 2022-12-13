package ua.com.foxminded.sql_jdbc_school.spring_events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.event.ContextRefreshedEvent;
import ua.com.foxminded.sql_jdbc_school.dao.jdbc_template.JDBCTemplateDBInitializer;
import ua.com.foxminded.sql_jdbc_school.service_db.SchoolDataGenerator;

class InitDBSpringEventListenerTest {
    @Mock
    JDBCTemplateDBInitializer dbInitializer;

    @Mock
    SchoolDataGenerator schoolDataGenerator;
    @Mock
    ContextRefreshedEvent event;

    InitDBSpringEventListener listener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listener = new InitDBSpringEventListener(dbInitializer, schoolDataGenerator);
    }

    @Test
    void onApplicationEvent_InvokeInitAllTablesMethodInDBInitalizer() {
        Mockito.doNothing().when(dbInitializer).initAllTables();
        listener.onApplicationEvent(event);
        Mockito.verify(dbInitializer, Mockito.times(1)).initAllTables();
    }
}