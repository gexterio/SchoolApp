package ua.com.foxminded.sqlJdbcSchool.dao.JDBC_Template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqlJdbcSchool.dao.JDBC_Template.Mappers.StudentCountMapper;
import ua.com.foxminded.sqlJdbcSchool.dao.JDBC_Template.Mappers.StudentMapper;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StudentDAO {
    private static final String SEARCH_GROUPS_BY_STUDENT_COUNT_QUERY = "SELECT group_id, Count(student_id) as cnt FROM students WHERE group_id>0 GROUP BY students.group_id HAVING COUNT (student_id)<=? ORDER BY students.group_id";
    private static final String SEARCH_STUDENT_BY_ID_QUERY = "SELECT  student_id, first_name, last_name, group_id FROM students WHERE student_id = ?";
    private static final String ADD_STUDENT_TO_GROUP_QUERY = "UPDATE students SET group_id = ? WHERE student_id = ?";
    private static final String ADD_STUDENT_TO_COURSE_QUERY = "INSERT INTO personal_courses (student_id, course_id) VALUES (?, ?)";
    private static final String DELETE_STUDENT_FROM_COURSE_QUERY = "DELETE FROM personal_courses WHERE student_id = ? AND course_id = ?";
    private static final String GET_ALL_STUDENTS_QUERY = "SELECT student_id, first_name, last_name, group_id FROM students";
    private static final String DELETE_STUDENT_QUERY = "DELETE FROM students WHERE student_id = ?";
    private static final String CREATE_STUDENT_QUERY = "INSERT INTO students (student_id, first_name, last_name) VALUES (DEFAULT, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StudentDTO searchById(Integer id) {
        return jdbcTemplate.query(SEARCH_STUDENT_BY_ID_QUERY,
                        new Object[]{id}, new StudentMapper()).stream().findAny()
                .orElseThrow(() -> new IllegalArgumentException("student with id: " + id + " not found"));
    }

    public void addStudentToGroup(StudentDTO student, Integer groupId) {
        jdbcTemplate.update(ADD_STUDENT_TO_GROUP_QUERY, groupId, student.getStudentID());
    }

    public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        jdbcTemplate.update(ADD_STUDENT_TO_COURSE_QUERY, student.getStudentID(), course.getCourseId());
    }

    public void deleteStudentFromCourse(StudentDTO student, CourseDTO course) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE_QUERY, student.getStudentID(), course.getCourseId());
    }

    public List<StudentDTO> getAll() {
        return jdbcTemplate.query(GET_ALL_STUDENTS_QUERY, new StudentMapper());
    }

    public Map<Integer, Integer> searchGroupsByStudentCount(Integer studentCount) {
        return jdbcTemplate.query(SEARCH_GROUPS_BY_STUDENT_COUNT_QUERY,
                        new Object[]{studentCount}, new StudentCountMapper()).stream()
                .collect(Collectors.toMap(
                        key -> Integer.valueOf(key.split("_")[0]),
                        value -> Integer.valueOf(value.split("_")[1])));
    }

    public void delete(StudentDTO student) {
        jdbcTemplate.update(DELETE_STUDENT_QUERY, student.getStudentID());
    }

    public void create(StudentDTO student) {
        jdbcTemplate.update(CREATE_STUDENT_QUERY,
                student.getFirstName(), student.getLastName());
    }

    public void batchCreate(List<StudentDTO> students) {
        jdbcTemplate.batchUpdate(CREATE_STUDENT_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, students.get(i).getFirstName());
                ps.setString(2, students.get(i).getLastName());
            }

            @Override
            public int getBatchSize() {
                return students.size();
            }
        });
    }

    public void batchAddStudentToGroup(List<StudentDTO> students) {
        jdbcTemplate.batchUpdate(ADD_STUDENT_TO_GROUP_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, students.get(i).getGroupId());
                ps.setInt(2, students.get(i).getStudentID());
            }

            @Override
            public int getBatchSize() {
                return students.size();
            }
        });
    }

    public void batchAddStudentToCourse(Map<StudentDTO, CourseDTO> studentCourseMap) {
        List<StudentDTO> students = new ArrayList<>();
        List<CourseDTO> courses = new ArrayList<>();
        studentCourseMap.entrySet().forEach(entry -> {
            students.add(entry.getKey());
            courses.add(entry.getValue());
        });
        jdbcTemplate.batchUpdate(ADD_STUDENT_TO_COURSE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, students.get(i).getStudentID());
                ps.setInt(2, courses.get(i).getCourseId());
            }

            @Override
            public int getBatchSize() {
                return studentCourseMap.size();
            }
        });

    }

}
