package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao implements Dao {
    private final BasicConnectionPool connectionPool;

    public GroupDao(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<GroupDTO> getAll() {
        Connection connection = connectionPool.getConnection();
        List<GroupDTO> groupDTOList = new ArrayList<>();
        String selectAll = "SELECT group_id, group_name FROM groups;";
        try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                String groupName = resultSet.getString("group_name");
                groupDTOList.add(new GroupDTO.GroupBuilder(groupName).setId(groupId).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return groupDTOList;
    }

    public void create(GroupDTO group) {
        Connection connection = connectionPool.getConnection();
        String insert = "INSERT INTO groups (group_id, group_name) VALUES (DEFAULT, (?)) RETURNING group_id";
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, group.getGroupName());
            statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

}
