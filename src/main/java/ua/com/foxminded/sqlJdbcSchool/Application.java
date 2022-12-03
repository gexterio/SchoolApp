package ua.com.foxminded.sqlJdbcSchool;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.sqlJdbcSchool.menu.Menu;
import ua.com.foxminded.sqlJdbcSchool.servicedb.SchoolDataGenerator;
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.getBean("schoolDataGenerator", SchoolDataGenerator.class).generateSchoolData();
        context.getBean("menu", Menu.class).run();
        context.close();
    }

}
