package ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.Mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseMapper implements RowMapper<CourseDTO> {
    @Override
    public CourseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CourseDTO.CourseBuilder(rs.getString("course_name"))
                .setCourseId(rs.getInt("course_id"))
                .setDescription(rs.getString("course_description"))
                .build();
    }
}
