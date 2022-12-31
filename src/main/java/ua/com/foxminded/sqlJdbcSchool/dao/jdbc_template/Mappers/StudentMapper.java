package ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.Mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<StudentDTO> {
    @Override
    public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StudentDTO.StudentBuilder(rs.getString("first_name"), rs.getString("last_name"))
                .setStudentId(rs.getInt("student_id"))
                .setGroupId(rs.getInt("group_id"))
                .build();
    }
}
