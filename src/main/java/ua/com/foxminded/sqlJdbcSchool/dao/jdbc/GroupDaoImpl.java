package ua.com.foxminded.sqlJdbcSchool.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqlJdbcSchool.dao.GroupDao;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqlJdbcSchool.dto.GroupDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {
    private static final String SELECT_ALL_GROUPS = "SELECT group_id, group_name FROM groups;";
    private static final String CREATE_GROUP = "INSERT INTO groups (group_id, group_name) VALUES (DEFAULT, (?))";
    private final BasicConnectionPool connectionPool;

    @Autowired
    public GroupDaoImpl(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<GroupDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<GroupDTO> groupDTOList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GROUPS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                String groupName = resultSet.getString("group_name");
                groupDTOList.add(new GroupDTO.GroupBuilder(groupName).setId(groupId).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Issue with getAll method");
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return groupDTOList;
    }

    @Override
    public void create(GroupDTO group) {
        if (group == null) {
            throw new IllegalArgumentException("GroupDTO can't be NULL");
        }
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GROUP)) {
            statement.setString(1, group.getGroupName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Issue with create method");
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

}
