package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements Dao {
    private BasicConnectionPool connectionPool;

    public CourseDao(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<CourseDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<CourseDTO> courseDTOList = new ArrayList<>();
        String selectAll = "SELECT course_id, course_name, course_description FROM courses;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("course_id");
                String name = resultSet.getString("course_name");
                String description = resultSet.getString("course_description");
                courseDTOList.add(new CourseDTO.CourseBuilder(name)
                        .setId(id)
                        .setDescription(description)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return courseDTOList;
    }

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

    public CourseDTO read(String s) {
        return null;
    }

    public void update(CourseDTO model) {

    }

    public void delete(CourseDTO model) {

    }

}
