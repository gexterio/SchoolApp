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
    private final BasicConnectionPool connectionPool;

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

    public List<Integer> getAllStudentsInCourse(CourseDTO courseDTO) {
        Connection connection = connectionPool.getConnection();
        List<Integer> idList = new ArrayList<>();
        String select = "SELECT student_id FROM personal_courses Where course_id = (?);";
        try (PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setInt(1, courseDTO.getCourseId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                idList.add(studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return idList;
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

}
