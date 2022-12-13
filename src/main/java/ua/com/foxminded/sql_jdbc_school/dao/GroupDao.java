package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.util.List;

public interface GroupDao {

    void create(GroupDTO group);

    List<GroupDTO> getAll();

}
