package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.PersonalCoursesDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonalCoursesDao implements Dao<PersonalCoursesDTO, String> {
    BasicConnectionPool connectionPool;

    public PersonalCoursesDao(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
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

    @Override
    public List<PersonalCoursesDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<PersonalCoursesDTO> personalCoursesDTOList = new ArrayList<>();
        String selectAll = "SELECT course_id, student_id FROM personal_courses;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                int studentId = resultSet.getInt("student_id");
                personalCoursesDTOList.add(new PersonalCoursesDTO(studentId, courseId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return personalCoursesDTOList;
    }

    @Override
    public void create(PersonalCoursesDTO model) {
        // replaced by addStudentToCourse(StudentDTO, CourseDTO)
    }

    @Override
    public PersonalCoursesDTO read(String s) {
        return null;
    }

    @Override
    public void update(PersonalCoursesDTO model) {

    }

    @Override
    public void delete(PersonalCoursesDTO model) {

    }

    public void deleteStudentFromCourse(StudentDTO student, CourseDTO course) {
        Connection connection = connectionPool.getConnection();
        List<PersonalCoursesDTO> list = new ArrayList<>();
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
}
