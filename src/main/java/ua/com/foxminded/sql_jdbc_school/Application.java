package ua.com.foxminded.sql_jdbc_school;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.sql_jdbc_school.config.SpringConfig;
import ua.com.foxminded.sql_jdbc_school.menu.Menu;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.getBean("menu", Menu.class).run();
        context.close();
    }

}
