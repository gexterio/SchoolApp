package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentDao implements Dao {

    private final BasicConnectionPool connectionPool;

    public StudentDao(BasicConnectionPool pool) {

        this.connectionPool = pool;
    }

    public StudentDTO searchById (int id) {
        Connection connection = connectionPool.getConnection();
        String select = "SELECT  first_name, last_name, group_id FROM students WHERE student_id = (?);";
        try (PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            int groupId = resultSet.getInt("group_id");
            return new StudentDTO.StudentBuilder(firstName, lastName).setStudentId(id).setGroupId(groupId).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Student not found");
    }
    public void addStudentToGroup(StudentDTO student, Integer groupId) {
        Connection connection = connectionPool.getConnection();
        String insertGroup = "UPDATE students SET group_id = (?) WHERE student_id = (?);";
        try (PreparedStatement statement = connection.prepareStatement(insertGroup)) {
            statement.setInt(1, groupId);
            statement.setInt(2, student.getStudentID());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        Connection connection = connectionPool.getConnection();
        String insertPersonalCourses = "INSERT INTO personal_courses (student_id, course_id) VALUES ((?), (?));";
        try (PreparedStatement statement = connection.prepareStatement(insertPersonalCourses)) {
            statement.setInt(1, student.getStudentID());
            statement.setInt(2, course.getCourseId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void deleteStudentFromCourse(StudentDTO student, CourseDTO course) {
        Connection connection = connectionPool.getConnection();
        String select = "DELETE FROM personal_courses WHERE student_id = (?) AND course_id = (?);";
        try (PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setInt(1, student.getStudentID());
            statement.setInt(2, course.getCourseId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void create(StudentDTO student) {
        Connection connection = connectionPool.getConnection();
        String insert = "INSERT INTO students (student_id, first_name, last_name) VALUES (DEFAULT, (?), (?)) RETURNING student_id";
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public List<StudentDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        String selectAll = "SELECT student_id, first_name, last_name, group_id FROM students;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int groupId = resultSet.getInt("group_id");
                studentDTOList.add(new StudentDTO.StudentBuilder(firstName, lastName).setStudentId(studentId).setGroupId(groupId).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        studentDTOList.sort(Comparator.comparing(StudentDTO::getStudentID));
        return studentDTOList;
    }

    public void delete(StudentDTO student) {
        Connection connection = connectionPool.getConnection();
        String delete = "DELETE FROM students WHERE student_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, student.getStudentID());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


}
