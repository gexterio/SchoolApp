package ua.com.foxminded.sqlJdbcSchool.springEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateDBInitializer;

@Component
public class InitDBSpringEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private JDBCTemplateDBInitializer dbInitializer;

    @Autowired
    public InitDBSpringEventListener(JDBCTemplateDBInitializer dbInitializer) {
        this.dbInitializer = dbInitializer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        dbInitializer.initAllTables();
    }
}
