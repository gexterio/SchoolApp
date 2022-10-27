package ua.com.foxminded.sql_jdbc_school;


import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.DBInitializer;
import ua.com.foxminded.sql_jdbc_school.dao.GroupDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.menu.Menu;
import ua.com.foxminded.sql_jdbc_school.servicedb.SchoolDataGenerator;
import ua.com.foxminded.sql_jdbc_school.util.FileReader;

import java.io.IOException;
import java.util.Properties;

public class Application {

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader().readProperties("properties.properties"));
        DBInitializer dbInitializer = new DBInitializer(
                prop.getProperty("url"),
                prop.getProperty("user"),
                prop.getProperty("password"));
        BasicConnectionPool connectionPool = dbInitializer.getConnectionPool();
        StudentDao studentDao = new StudentDao(connectionPool);
        GroupDao groupDao = new GroupDao(connectionPool);
        CourseDao courseDao = new CourseDao(connectionPool);
        new SchoolDataGenerator(connectionPool, studentDao, groupDao, courseDao)
                .generateSchoolData();
        Menu menu = new Menu(connectionPool, studentDao, courseDao);
        menu.run();
    }

}
