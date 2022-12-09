package ua.com.foxminded.sqlJdbcSchool.springEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateDBInitializer;
import ua.com.foxminded.sqlJdbcSchool.servicedb.SchoolDataGenerator;

@Component
public class InitDBSpringEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private JDBCTemplateDBInitializer dbInitializer;
    private SchoolDataGenerator schoolDataGenerator;
@Autowired
    public InitDBSpringEventListener(JDBCTemplateDBInitializer dbInitializer, SchoolDataGenerator schoolDataGenerator) {
        this.dbInitializer = dbInitializer;
        this.schoolDataGenerator = schoolDataGenerator;
    System.out.println("DB is created");
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        dbInitializer.initAllTables();
        schoolDataGenerator.generateSchoolData();
    }
}
