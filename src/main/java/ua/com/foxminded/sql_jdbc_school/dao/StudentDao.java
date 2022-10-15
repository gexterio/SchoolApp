package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class StudentDao implements Dao<StudentDTO, String> {

    private BasicConnectionPool connectionPool;

    Map<Integer, StudentDTO> studentDTOMap = new HashMap<>();
    Map<Integer, CourseDTO> personalCourses = new HashMap<>();

    @Override
    public void addToCache(StudentDTO student) {
        studentDTOMap.put(student.getStudentID(), student);
    }

    public void addToCache(CourseDTO course, StudentDTO student) {
        personalCourses.put(student.getStudentID(), course);
    }

    private final String insert = "INSERT INTO students (student_id, first_name, last_name) VALUES (DEFAULT, (?), (?)) RETURNING student_id";
    private final String insertGroup = "UPDATE students SET group_id = (?) WHERE student_id = (?) RETURNING student_id;";
    private final String insertPersonalCourses = "INSERT INTO personal_courses (student_id, course_id) VALUES ((?), (?)) RETURNING student_id;";

    public StudentDao(BasicConnectionPool pool) {
        this.connectionPool = pool;
    }

    public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insertPersonalCourses)) {
            statement.setInt(1, student.getStudentID()+1);
            statement.setInt(2, course.getCourseId()+1);
            statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public Map<Integer, StudentDTO> getStudentDTOMap() {
        return studentDTOMap;
    }


    public boolean addStudentToGroup(StudentDTO student, Integer group_id) {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insertGroup)) {
            ;
            statement.setInt(1, group_id);
            statement.setInt(2, student.getStudentID());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public boolean create(StudentDTO student) {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            ;
            statement.setString(1, student.getStudentFirstName());
            statement.setString(2, student.getStudentLastName());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        addToCache(student);
        return result;
    }

    @Override
    public StudentDTO read(String s) {
        return null;
    }

    @Override
    public boolean update(StudentDTO model) {
        return false;
    }

    @Override
    public boolean delete(StudentDTO model) {
        return false;
    }


}
