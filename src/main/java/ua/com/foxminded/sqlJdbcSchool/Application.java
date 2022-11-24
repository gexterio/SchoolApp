package ua.com.foxminded.sqlJdbcSchool;


import ua.com.foxminded.sqlJdbcSchool.dao.CourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.DBInitializer;
import ua.com.foxminded.sqlJdbcSchool.dao.GroupDao;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqlJdbcSchool.menu.Menu;
import ua.com.foxminded.sqlJdbcSchool.servicedb.SchoolDataGenerator;
import ua.com.foxminded.sqlJdbcSchool.util.FileReader;

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
