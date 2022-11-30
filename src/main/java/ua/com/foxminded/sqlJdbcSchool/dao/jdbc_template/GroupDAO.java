package ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqlJdbcSchool.dao.Dao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.Mappers.GroupMapper;
import ua.com.foxminded.sqlJdbcSchool.dto.GroupDTO;
import ua.com.foxminded.sqlJdbcSchool.util.DTOInputValidator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GroupDAO implements Dao<GroupDTO> {

    private static final String GET_ALL_GROUPS_QUERY = "SELECT group_id, group_name FROM groups";
    private static final String CREATE_GROUP_QUERY = "INSERT INTO groups (group_id, group_name) VALUES (DEFAULT, ?)";
    private final JdbcTemplate jdbcTemplate;
    private final DTOInputValidator validator;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupDAO(JdbcTemplate jdbcTemplate, DTOInputValidator validator, GroupMapper groupMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.validator = validator;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<GroupDTO> getAll() {
        return jdbcTemplate.query(GET_ALL_GROUPS_QUERY, groupMapper);
    }

    @Override
    public void create(GroupDTO group) {
        validator.validateGroup(group);
        jdbcTemplate.update(CREATE_GROUP_QUERY, group.getGroupName());
    }

    public void batchCreate(List<GroupDTO> groups) {
        groups.forEach(validator::validateGroup);
        jdbcTemplate.batchUpdate(CREATE_GROUP_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, groups.get(i).getGroupName());
            }

            @Override
            public int getBatchSize() {
                return groups.size();
            }
        });
    }
}
