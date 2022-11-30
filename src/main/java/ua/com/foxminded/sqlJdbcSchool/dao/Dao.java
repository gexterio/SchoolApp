package ua.com.foxminded.sqlJdbcSchool.dao;

import java.util.List;

public interface Dao<T> {

    public List<T> getAll();

    public void create (T entity);
}
