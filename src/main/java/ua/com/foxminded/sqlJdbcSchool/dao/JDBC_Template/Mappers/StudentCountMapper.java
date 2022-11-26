package ua.com.foxminded.sqlJdbcSchool.dao.JDBC_Template.Mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCountMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        int groupId = rs.getInt("group_id");
        int cnt = rs.getInt("cnt");
        return groupId + "_" + cnt;
    }
}
