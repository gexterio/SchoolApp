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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDao {

    public static final String CREATE_STUDENT = "INSERT INTO students (student_id, first_name, last_name) VALUES (DEFAULT, (?), (?))";
    private static final String ADD_STUDENT_TO_COURSE = "INSERT INTO personal_courses (student_id, course_id) VALUES ((?), (?));";
    private static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM personal_courses WHERE student_id = (?) AND course_id = (?);";
    private static final String SELECT_ALL_STUDENTS = "SELECT student_id, first_name, last_name, group_id FROM students;";
    private static final String SELECT_COUNT_STUDENTS_IN_GROUP = "SELECT group_id, Count(student_id) as cnt FROM students WHERE group_id>0 GROUP BY students.group_id HAVING COUNT (student_id)<=(?) ORDER BY students.group_id;";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE student_id = (?)";
    private static final String SELECT_BY_ID = "SELECT  first_name, last_name, group_id FROM students WHERE student_id = (?);";
    private static final String SET_GROUP_ID = "UPDATE students SET group_id = (?) WHERE student_id = (?);";
    public static final String GROUP_ID = "group_id";
    private final BasicConnectionPool connectionPool;

    public StudentDao(BasicConnectionPool pool) {

        this.connectionPool = pool;
    }

    public StudentDTO searchById(Integer id) {
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
        }
        throw new IllegalArgumentException("Student not found");
    }

    public void addStudentToGroup(StudentDTO student, int groupId) {
        if (student.getStudentID() == null) {
            throw new IllegalArgumentException("student id can't be NULL");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SET_GROUP_ID)) {
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
        try (PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_TO_COURSE)) {
            statement.setInt(1, student.getStudentID());
            statement.setInt(2, course.getCourseId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Issue with addStudentToCourse");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void deleteStudentFromCourse(StudentDTO student, CourseDTO course) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_FROM_COURSE)) {
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
        studentDTOList.sort(Comparator.comparing(StudentDTO::getStudentID));
        return studentDTOList;
    }

    public Map<Integer, Integer> searchGroupsByStudentCount(int studentCount) {
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

    public void delete(StudentDTO student) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT)) {
            statement.setInt(1, student.getStudentID());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issue with delete students");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


}
