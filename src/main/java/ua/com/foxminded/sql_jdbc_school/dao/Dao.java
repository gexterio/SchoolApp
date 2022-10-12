package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

public interface Dao <Entity, Key> {
    boolean create(Entity model);
    Entity read(Key key);
    boolean update(Entity model);
    boolean delete(Entity model);
    void addToCache(Entity model);
}
