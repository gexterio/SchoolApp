package ua.com.foxminded.sqlJdbcSchool.dao.JDBC_Template.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<CourseDTO> {
    @Override
    public CourseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CourseDTO.CourseBuilder(rs.getString("course_name"))
                .setCourseId(rs.getInt("course_id"))
                .setDescription(rs.getString("course_description"))
                .build();
    }
}
