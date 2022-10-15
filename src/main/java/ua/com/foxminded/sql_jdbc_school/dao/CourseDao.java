package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CourseDao implements Dao<CourseDTO, String> {
    private BasicConnectionPool connectionPool;

    public Map<Integer, CourseDTO> getCourseMap() {
        return courseMap;
    }

    private Map<Integer, CourseDTO> courseMap = new HashMap<>();
    private final String insert = "INSERT INTO courses (course_id, course_name, course_description) VALUES (DEFAULT, (?), (?)) RETURNING course_id";

    public CourseDao(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public boolean create(CourseDTO course) {
        boolean result = false;
        addToCache(course);
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public CourseDTO read(String s) {
        return null;
    }

    @Override
    public boolean update(CourseDTO model) {
        return false;
    }

    @Override
    public boolean delete(CourseDTO model) {
        return false;
    }

    @Override
    public void addToCache(CourseDTO course) {
        courseMap.put(course.getCourseId(), course);
    }
}
