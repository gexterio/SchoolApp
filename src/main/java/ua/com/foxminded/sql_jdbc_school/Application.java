package ua.com.foxminded.sql_jdbc_school;


import ua.com.foxminded.sql_jdbc_school.dao.*;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;
import ua.com.foxminded.sql_jdbc_school.servicedb.SchoolDataGenerator;

public class Application {
    private static final String URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1101";

    public static void main(String[] args) {

        DBInitializer dbInitializer = new DBInitializer(URL, USER, PASSWORD);
        BasicConnectionPool connectionPool = dbInitializer.getConnectionPool();
        StudentDao studentDao = new StudentDao(connectionPool);
        GroupDao groupDao = new GroupDao(connectionPool);
        CourseDao courseDao = new CourseDao(connectionPool);
        PersonalCoursesDao personalCourseDao = new PersonalCoursesDao(connectionPool);
        new SchoolDataGenerator(connectionPool, studentDao, groupDao, courseDao, personalCourseDao)
                .generateSchoolData();
//        for (StudentDTO studentDTO : studentDao.getAll()) {
//            System.out.println(studentDTO.toString());
//        }
        connectionPool.closePoolConnection();
    }

}
