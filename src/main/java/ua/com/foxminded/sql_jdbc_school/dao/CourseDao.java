package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements Dao<CourseDTO, String> {
    private BasicConnectionPool connectionPool;


    public CourseDao(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<CourseDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<CourseDTO> courseDTOList = new ArrayList<>();
        String selectAll = "SELECT course_id, course_name, course_description FROM courses;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                String courseDescription = resultSet.getString("course_description");
                courseDTOList.add(new CourseDTO(courseId, courseName, courseDescription));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return courseDTOList;
    }

    @Override
    public void create(CourseDTO course) {
        Connection connection = connectionPool.getConnection();
        String insert = "INSERT INTO courses (course_id, course_name, course_description) VALUES (DEFAULT, (?), (?)) RETURNING course_id";
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
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

}
