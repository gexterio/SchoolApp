package ua.com.foxminded.sqljdbcschool.dao.jdbc_template.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dto.GroupDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupMapper implements RowMapper<GroupDTO> {
    @Override
    public GroupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GroupDTO.GroupBuilder(rs.getString("group_name"))
                .setId(rs.getInt("group_id"))
                .build();
    }
}
