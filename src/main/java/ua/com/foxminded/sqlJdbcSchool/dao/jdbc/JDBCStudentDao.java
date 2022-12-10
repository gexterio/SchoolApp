package ua.com.foxminded.sqlJdbcSchool.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JDBCStudentDao implements StudentDao {

    public static final String CREATE_STUDENT = "INSERT INTO students (student_id, first_name, last_name) VALUES (DEFAULT, (?), (?))";
    public static final String GROUP_ID = "group_id";
    public static final IllegalArgumentException STUDENT_NULL_EXCEPTION = new IllegalArgumentException("student can't be NULL");
    private static final String ADD_STUDENT_TO_COURSE = "INSERT INTO personal_courses (student_id, course_id) VALUES ((?), (?));";
    private static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM personal_courses WHERE student_id = (?) AND course_id = (?);";
    private static final String SELECT_ALL_STUDENTS = "SELECT student_id, first_name, last_name, group_id FROM students;";
    private static final String SELECT_COUNT_STUDENTS_IN_GROUP = "SELECT group_id, Count(student_id) as cnt FROM students WHERE group_id>0 GROUP BY students.group_id HAVING COUNT (student_id)<=(?) ORDER BY students.group_id;";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE student_id = (?)";
    private static final String SELECT_BY_ID = "SELECT  first_name, last_name, group_id FROM students WHERE student_id = (?);";
    private static final String SET_GROUP_ID = "UPDATE students SET group_id = (?) WHERE student_id = (?);";
    private final BasicConnectionPool connectionPool;

    @Autowired
    public JDBCStudentDao(BasicConnectionPool pool) {

        this.connectionPool = pool;
    }

    @Override
    public StudentDTO searchById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id can't be NULL");
        }
        if (id == 0) {
            throw new IllegalArgumentException("id can't be 0");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            int groupId = resultSet.getInt(GROUP_ID);
            return new StudentDTO.StudentBuilder(firstName, lastName).setStudentId(id).setGroupId(groupId).build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Student not found");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void addStudentToGroup(StudentDTO student, Integer groupId) {
        if (student == null) {
            throw STUDENT_NULL_EXCEPTION;
        }
        if (groupId == null) {
            throw new IllegalArgumentException("groupId can't be NULL");
        }
        if (student.getStudentId() == null) {
            throw new IllegalArgumentException("student id can't be NULL");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SET_GROUP_ID)) {
            statement.setInt(1, groupId);
            statement.setInt(2, student.getStudentId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        if (student == null) {
            throw STUDENT_NULL_EXCEPTION;
        }
        if (course == null) {
            throw new IllegalArgumentException("course can't be NULL");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_TO_COURSE)) {
            statement.setInt(1, student.getStudentId());
            statement.setInt(2, course.getCourseId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Issue with addStudentToCourse");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteStudentFromCourse(StudentDTO student, CourseDTO course) {
        if (student == null) {
            throw STUDENT_NULL_EXCEPTION;
        }
        if (course == null) {
            throw new IllegalArgumentException("course can't be NULL");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_FROM_COURSE)) {
            statement.setInt(1, student.getStudentId());
            statement.setInt(2, course.getCourseId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Issue with deleteStudentFromCourse method");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void create(StudentDTO student) {
        if (student == null) {
            throw new IllegalArgumentException("StudentDTO can't be NULL");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_STUDENT)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issue with create student");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<StudentDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STUDENTS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int groupId = resultSet.getInt(GROUP_ID);
                studentDTOList.add(new StudentDTO.StudentBuilder(firstName, lastName).setStudentId(studentId).setGroupId(groupId).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issue with getAll students");
        } finally {
            connectionPool.releaseConnection(connection);
        }
        studentDTOList.sort(Comparator.comparing(StudentDTO::getStudentId));
        return studentDTOList;
    }

    @Override
    public Map<Integer, Integer> searchGroupsByStudentCount(Integer studentCount) {
        if (studentCount == null) {
            throw new IllegalArgumentException("studentCount can't be NULL");
        }
        if (studentCount < 0) {
            throw new IllegalArgumentException("studentCount can't be <0");
        }
        Connection connection = connectionPool.getConnection();
        Map<Integer, Integer> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_STUDENTS_IN_GROUP)) {
            statement.setInt(1, studentCount);
            ResultSet resultSet = statement.executeQuery();
            result = new HashMap<>();
            while (resultSet.next()) {
                int groupId = resultSet.getInt(GROUP_ID);
                int count = resultSet.getInt("cnt");
                result.put(groupId, count);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issue with searchGroupsByStudentCount");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(StudentDTO student) {
        if (student == null) {
            throw STUDENT_NULL_EXCEPTION;
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT)) {
            statement.setInt(1, student.getStudentId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issue with delete students");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
