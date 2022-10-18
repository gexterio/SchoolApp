package ua.com.foxminded.sql_jdbc_school.dao;


import java.util.List;

public interface Dao <Entity, Key> {
    void create(Entity model);
    Entity read(Key key);
    void update(Entity model);
    void delete(Entity model);
    List<Entity> getAll();
}
