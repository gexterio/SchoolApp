package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBInitializer {
    private BasicConnectionPool basicConnectionPool;

    public DBInitializer(String url, String user, String password) {
        basicConnectionPool = new BasicConnectionPool(url, user, password);
        initALLTables();
    }

    public BasicConnectionPool getConnectionPool() {
        return basicConnectionPool;
    }

    public void initALLTables() {
        List<String> sqlQueryList = getSqlQuery();
        for (String s : sqlQueryList) {
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
        list.add("DROP TABLE IF EXISTS personal_courses, students, groups, courses;");
        list.add("CREATE TABLE groups (group_id SERIAL NOT NULL PRIMARY KEY, group_name VARCHAR(5) NOT NULL);");
        list.add("CREATE TABLE courses (course_id SERIAL NOT NULL PRIMARY KEY, course_name VARCHAR(32) NOT NULL, " +
                "course_description VARCHAR(256) NOT NULL);");
        list.add("CREATE TABLE students (student_id SERIAL NOT NULL PRIMARY KEY, first_name VARCHAR(32) NOT NULL," +
                "last_name VARCHAR(32) NOT NULL, group_id INTEGER  REFERENCES groups(group_id))");
        list.add("CREATE TABLE personal_courses (student_id INTEGER NOT NULL  REFERENCES students(student_id) on delete cascade," +
                "course_id INTEGER NOT NULL REFERENCES courses(course_id)," +
                " CONSTRAINT pair PRIMARY KEY (student_id, course_id));");
        return list;
    }
}

