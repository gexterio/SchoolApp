package ua.com.foxminded.sql_jdbc_school.dao.jdbc_template.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentCountMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        int groupId = rs.getInt("group_id");
        int cnt = rs.getInt("cnt");
        return groupId + "_" + cnt;
    }
}
