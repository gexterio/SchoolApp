package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GroupDao implements Dao<GroupDTO, String> {
    private BasicConnectionPool connectionPool;
    private Map<Integer, GroupDTO> groupMap = new HashMap<>();
    private final String insert = "INSERT INTO groups (group_id, group_name) VALUES (DEFAULT, (?)) RETURNING group_id";
    private final String selectID = "SELECT group_id FROM groups WHERE group_name = (?)";
    public GroupDao(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public boolean create(GroupDTO group) {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, group.getGroupName());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        addToCache(group);
        return result;
    }
    @Override
    public void addToCache(GroupDTO group) {
        Connection connection = connectionPool.getConnection();
        int groupID = -1;
        try (PreparedStatement statement = connection.prepareStatement(selectID)) {
            statement.setString(1, group.getGroupName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                 groupID = resultSet.getInt("group_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        groupMap.put(groupID, group);
    }

    @Override
    public GroupDTO read(String s) {
        return null;
    }

    @Override
    public boolean update(GroupDTO model) {
        return false;
    }

    @Override
    public boolean delete(GroupDTO model) {
        return false;
    }
}
