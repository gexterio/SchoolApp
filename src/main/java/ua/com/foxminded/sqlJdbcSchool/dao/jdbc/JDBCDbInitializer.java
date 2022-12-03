package ua.com.foxminded.sqlJdbcSchool.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.foxminded.sqlJdbcSchool.dao.DbInitializer;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JDBCDbInitializer implements DbInitializer {
    public static final String CREATE_COURSES = "CREATE TABLE courses (course_id SERIAL NOT NULL PRIMARY KEY, course_name VARCHAR(32) NOT NULL,course_description VARCHAR(256) NOT NULL);";
    public static final String CREATE_STUDENTS = "CREATE TABLE students (student_id SERIAL NOT NULL PRIMARY KEY, first_name VARCHAR(32) NOT NULL,last_name VARCHAR(32) NOT NULL, group_id INTEGER  REFERENCES groups(group_id))";
    public static final String CREATE_PERSONAL_COURSES = "CREATE TABLE personal_courses (student_id INTEGER NOT NULL  REFERENCES students(student_id) on delete cascade,course_id INTEGER NOT NULL REFERENCES courses(course_id),CONSTRAINT pair PRIMARY KEY (student_id, course_id));";
    private static final String CREATE_GROUPS = "CREATE TABLE groups (group_id SERIAL NOT NULL PRIMARY KEY, group_name VARCHAR(5) NOT NULL);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS personal_courses, students, groups, courses;";
    private final BasicConnectionPool connectionPool;

    @Autowired
    public JDBCDbInitializer(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @PostConstruct
    public void initAllTables() {
        for (String s : getSqlQuery()) {
            initTable(s);
        }
    }

    @Override
    public void initTable(String sqlQuery) {
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

    @Override
    public List<String> getSqlQuery() {
        List<String> list = new ArrayList<>();
        list.add(DROP_TABLE);
        list.add(CREATE_GROUPS);
        list.add(CREATE_COURSES);
        list.add(CREATE_STUDENTS);
        list.add(CREATE_PERSONAL_COURSES);
        return list;
    }
}

