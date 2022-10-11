package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBInitializer {

   private  BasicConnectionPool connectionPool;

    public DBInitializer(String url, String user, String password) {
        try {
            this.connectionPool = BasicConnectionPool.create(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initALLTables();
    }

    public BasicConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public  void initALLTables() {
        List<String> sqlQueryList = getSqlQuery();
        for (String s : sqlQueryList) {
            initTable(s);
        }
    }

    private void initTable(String sqlQuery) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlQuery)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private List<String> getSqlQuery() {
        List<String> list = new ArrayList<>();
        list.add("DROP TABLE IF EXISTS personal_courses, students, groups, courses;");
        list.add("CREATE TABLE groups (group_id INTEGER NOT NULL PRIMARY KEY, group_name VARCHAR(5) NOT NULL);");
        list.add("CREATE TABLE courses (course_id INTEGER NOT NULL PRIMARY KEY, course_name VARCHAR(32) NOT NULL, " +
                "course_description VARCHAR(32) NOT NULL);");
        list.add("CREATE TABLE students (student_id INTEGER NOT NULL PRIMARY KEY, first_name VARCHAR(32) NOT NULL," +
                "last_name VARCHAR(32) NOT NULL, group_id INTEGER NOT NULL REFERENCES groups(group_id))");
        list.add("CREATE TABLE personal_courses (id INTEGER NOT NULL PRIMARY KEY,\n" +
                "student_id INTEGER NOT NULL  REFERENCES students(student_id),\n" +
                "course_id INTEGER NOT NULL REFERENCES courses(course_id)\n" +
                ");");
        return list;
    }
}

