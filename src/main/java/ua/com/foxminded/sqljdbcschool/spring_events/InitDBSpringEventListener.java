package ua.com.foxminded.sqljdbcschool.spring_events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dao.DbInitializer;
import ua.com.foxminded.sqljdbcschool.dao.jdbc_template.JDBCTemplateDBInitializer;
import ua.com.foxminded.sqljdbcschool.service_db.SchoolDataGenerator;

@Component
public class InitDBSpringEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DbInitializer dbInitializer;
    private final SchoolDataGenerator schoolDataGenerator;

    private int callCounter;

    @Autowired
    public InitDBSpringEventListener(@Qualifier("JDBCTemplateDBInitializer") DbInitializer dbInitializer, SchoolDataGenerator schoolDataGenerator) {
        this.dbInitializer = dbInitializer;
        this.schoolDataGenerator = schoolDataGenerator;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (callCounter >1) {
            throw new RuntimeException("DB init more than 1 time!!!");
        }
        if (callCounter == 0) {
            dbInitializer.initAllTables();
            schoolDataGenerator.generateSchoolData();
        }
        callCounter++;
    }
}
