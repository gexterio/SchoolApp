package ua.com.foxminded.sqljdbcschool.dao;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.sqljdbcschool.dto.GroupDTO;

import java.util.List;

public interface GroupDao {

    void create(GroupDTO group);

    @Transactional
    void batchCreate(List<GroupDTO> list);

    List<GroupDTO> getAll();

}
