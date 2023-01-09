package ua.com.foxminded.sqljdbcschool.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCCourseDao implements CourseDao {
    public static final String COURSE_DESCRIPTION = "course_description";
    public static final String COURSE_NOT_FOUND = "Course not found.";
    private static final String SELECT_COURSE_BY_ID = "SELECT  course_name, course_description FROM courses WHERE course_id = (?);";
    private static final String SELECT_COURSE_BY_NAME = "SELECT  course_id, course_description FROM courses WHERE course_name = (?);";
    private static final String SELECT_STUDENT_BY_COURSE_ID = "SELECT student_id FROM personal_courses WHERE course_id = (?);";
    private static final String SELECT_ALL_COURSES = "SELECT course_id, course_name, course_description FROM courses;";
    private static final String CREATE_COURSE = "INSERT INTO courses (course_id, course_name, course_description) VALUES (DEFAULT, (?), (?))";
    private final BasicConnectionPool connectionPool;

    @Autowired
    public JDBCCourseDao(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public CourseDTO searchById(int id) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String courseName = resultSet.getString("course_name");
            String courseDescription = resultSet.getString(COURSE_DESCRIPTION);
            return new CourseDTO.CourseBuilder(courseName).setCourseId(id).setDescription(courseDescription).build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(COURSE_NOT_FOUND + "ID = " + id);
        }
    }

    @Override
    public CourseDTO searchByName(String name) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int courseId = resultSet.getInt("course_id");
            String courseDescription = resultSet.getString(COURSE_DESCRIPTION);
            return new CourseDTO.CourseBuilder(name).setCourseId(courseId).setDescription(courseDescription).build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(COURSE_NOT_FOUND + "Name = " + name);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Integer> searchStudentsInCourse(String courseName) {
        int courseId = searchByName(courseName).getCourseId();
        Connection connection = connectionPool.getConnection();
        List<Integer> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_COURSE_ID)) {
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                list.add(studentId);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(COURSE_NOT_FOUND + "Name = " + courseName);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void batchCreate(List<CourseDTO> courses) {
courses.forEach(this::create);
    }

    @Override
    public List<CourseDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<CourseDTO> courseDTOList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COURSES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("course_id");
                String name = resultSet.getString("course_name");
                String description = resultSet.getString(COURSE_DESCRIPTION);
                courseDTOList.add(new CourseDTO.CourseBuilder(name)
                        .setCourseId(id)
                        .setDescription(description)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Issue with getAll method");
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return courseDTOList;
    }

    @Override
    public void create(CourseDTO course) {
        if (course == null) {
            throw new IllegalArgumentException("courseDTO can't be NULL");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_COURSE)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Issue with create method");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

}
