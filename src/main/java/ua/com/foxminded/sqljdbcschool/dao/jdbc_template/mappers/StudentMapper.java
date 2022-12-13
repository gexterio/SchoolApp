package ua.com.foxminded.sqljdbcschool.dao.jdbc_template.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;

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
