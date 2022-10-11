package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dao.connection.ConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;


public class StudentDao implements Dao {

    private BasicConnectionPool connectionPool;

    List<StudentDTO> studentDTOList;

    public StudentDao( BasicConnectionPool pool) {
            this.connectionPool = pool;
    }


    @Override
    public boolean create(Object model) {
        return false;
    }

    @Override
    public Object read(Object o) {
        return null;
    }

    @Override
    public boolean update(Object model) {
        return false;
    }

    @Override
    public boolean delete(Object model) {
        return false;
    }
}
