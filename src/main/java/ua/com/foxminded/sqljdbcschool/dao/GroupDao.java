package ua.com.foxminded.sqljdbcschool.dao;

import ua.com.foxminded.sqljdbcschool.dto.GroupDTO;

import java.util.List;

public interface GroupDao {

    void create(GroupDTO group);

    List<GroupDTO> getAll();

}
