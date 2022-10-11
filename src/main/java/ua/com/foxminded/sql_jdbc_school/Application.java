package ua.com.foxminded.sql_jdbc_school;


import ua.com.foxminded.sql_jdbc_school.dao.DBInitializer;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.menu.Menu;

public class Application {
    private final static String URL = "jdbc:postgresql://localhost:5432/school";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1101";
    public static void main(String[] args) {

        DBInitializer dbInitializer = new DBInitializer(URL, USER, PASSWORD);
        StudentDao studentDao = new StudentDao(dbInitializer.getConnectionPool());


    }
}
