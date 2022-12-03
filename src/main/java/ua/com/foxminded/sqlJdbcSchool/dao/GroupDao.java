package ua.com.foxminded.sqlJdbcSchool.dao;

import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqlJdbcSchool.dto.GroupDTO;

import java.util.List;

public interface GroupDao {

    void create(GroupDTO group);

    List<GroupDTO> getAll();

}
