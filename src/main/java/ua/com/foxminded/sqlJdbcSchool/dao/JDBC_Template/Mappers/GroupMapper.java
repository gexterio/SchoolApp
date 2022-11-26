package ua.com.foxminded.sqlJdbcSchool.dao.JDBC_Template.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.sqlJdbcSchool.dto.GroupDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<GroupDTO> {
    @Override
    public GroupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GroupDTO.GroupBuilder(rs.getString("group_name"))
                .setId(rs.getInt("group_id"))
                .build();
    }
}