package ua.com.foxminded.sqljdbcschool.spring_events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dao.jdbc_template.JDBCTemplateDBInitializer;
import ua.com.foxminded.sqljdbcschool.service_db.SchoolDataGenerator;

@Component
public class InitDBSpringEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final JDBCTemplateDBInitializer dbInitializer;
    private final SchoolDataGenerator schoolDataGenerator;

    private int callCounter;

    @Autowired
    public InitDBSpringEventListener(JDBCTemplateDBInitializer dbInitializer, SchoolDataGenerator schoolDataGenerator) {
        this.dbInitializer = dbInitializer;
        this.schoolDataGenerator = schoolDataGenerator;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (callCounter == 0) {
            dbInitializer.initAllTables();
            schoolDataGenerator.generateSchoolData();
        }
        callCounter++;
    }
}
