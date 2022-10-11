package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

public class CourseDao implements Dao<CourseDTO, String> {
    public CourseDao(BasicConnectionPool connectionPool) {

    }

    @Override
    public boolean create(CourseDTO model) {
        return false;
    }

    @Override
    public CourseDTO read(String s) {
        return null;
    }

    @Override
    public boolean update(CourseDTO model) {
        return false;
    }

    @Override
    public boolean delete(CourseDTO model) {
        return false;
    }
}
