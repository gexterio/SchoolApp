package ua.com.foxminded.sqlJdbcSchool;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.com.foxminded.sqlJdbcSchool.menu.Menu;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.getBean("menu", Menu.class).run();
    }

}
