package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBInitializer {
    private final BasicConnectionPool basicConnectionPool;
    public static final String CREATE_COURSES = "CREATE TABLE courses (course_id SERIAL NOT NULL PRIMARY KEY, course_name VARCHAR(32) NOT NULL,course_description VARCHAR(256) NOT NULL);";
    private static final String CREATE_GROUPS = "CREATE TABLE groups (group_id SERIAL NOT NULL PRIMARY KEY, group_name VARCHAR(5) NOT NULL);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS personal_courses, students, groups, courses;";
    public static final String CREATE_STUDENTS = "CREATE TABLE students (student_id SERIAL NOT NULL PRIMARY KEY, first_name VARCHAR(32) NOT NULL,last_name VARCHAR(32) NOT NULL, group_id INTEGER  REFERENCES groups(group_id))";
    public static final String CREATE_PERSONAL_COURSES = "CREATE TABLE personal_courses (student_id INTEGER NOT NULL  REFERENCES students(student_id) on delete cascade,course_id INTEGER NOT NULL REFERENCES courses(course_id),CONSTRAINT pair PRIMARY KEY (student_id, course_id));";


    public DBInitializer(String url, String user, String password) {
        basicConnectionPool = new BasicConnectionPool(url, user, password);
        initAllTables();
    }

    public BasicConnectionPool getConnectionPool() {
        return basicConnectionPool;
    }

    public void initAllTables() {
        for (String s : getSqlQuery()) {
            initTable(s);
        }
    }

    private void initTable(String sqlQuery) {
        Connection connection = basicConnectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlQuery)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            basicConnectionPool.releaseConnection(connection);
        }
    }

    private List<String> getSqlQuery() {
        List<String> list = new ArrayList<>();
        list.add(DROP_TABLE);
        list.add(CREATE_GROUPS);
        list.add(CREATE_COURSES);
        list.add(CREATE_STUDENTS);
        list.add(CREATE_PERSONAL_COURSES);
        return list;
    }
}

