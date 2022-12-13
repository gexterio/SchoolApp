package ua.com.foxminded.sql_jdbc_school.dao.jdbc_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.jdbc_template.mappers.CourseMapper;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.util.DTOInputValidator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCTemplateCourseDao implements CourseDao {

    public static final String SEARCH_STUDENTS_IN_COURSE_QUERY = "SELECT student_id FROM personal_courses WHERE course_id = ?";
    public static final String SEARCH_COURSE_BY_NAME_QUERY = "SELECT  course_id, course_name, course_description FROM courses WHERE course_name = ?";
    public static final String CREATE_COURSE_QUERY = "INSERT INTO courses (course_id, course_name, course_description) VALUES (DEFAULT, ?, ?)";
    public static final String GET_ALL_COURSES_QUERY = "SELECT course_id, course_name, course_description FROM courses;";
    public static final String SEARCH_COURSE_BY_ID_QUERY = "SELECT course_id, course_name, course_description FROM courses WHERE course_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final DTOInputValidator validator;
    private final CourseMapper courseMapper;


    @Autowired
    public JDBCTemplateCourseDao(JdbcTemplate jdbcTemplate, DTOInputValidator validator, CourseMapper courseMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.validator = validator;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Integer> searchStudentsInCourse(String courseName) {
        Integer courseId = searchByName(courseName).getCourseId();
        return jdbcTemplate.query(SEARCH_STUDENTS_IN_COURSE_QUERY, new Object[]{courseId},
                (rs, rowNum) -> rs.getInt("student_id"));
    }

    @Override
    public CourseDTO searchByName(String name) {
        return jdbcTemplate.query(SEARCH_COURSE_BY_NAME_QUERY,
                        new Object[]{name}, new CourseMapper()).stream().findAny()
                .orElseThrow(() -> new IllegalArgumentException("course with name: " + name + " not found"));
    }

    @Override
    public void create(CourseDTO course) {
        validator.validateCourse(course);
        jdbcTemplate.update(CREATE_COURSE_QUERY,
                course.getCourseName(), course.getCourseDescription());
    }

    @Override
    public List<CourseDTO> getAll() {
        return jdbcTemplate.query(GET_ALL_COURSES_QUERY,
                new CourseMapper());
    }

    @Override
    public CourseDTO searchById(int id) {
        return jdbcTemplate.query(SEARCH_COURSE_BY_ID_QUERY,
                        new Object[]{id}, courseMapper).stream().findAny()
                .orElseThrow(() -> new IllegalArgumentException("course with id: " + id + " not found"));
    }

    public void batchCreate(List<CourseDTO> courses) {
        courses.forEach(validator::validateCourse);
        jdbcTemplate.batchUpdate(CREATE_COURSE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, courses.get(i).getCourseName());
                ps.setString(2, courses.get(i).getCourseDescription());
            }

            @Override
            public int getBatchSize() {
                return courses.size();
            }
        });
    }
}
