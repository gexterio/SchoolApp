package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class StudentDao implements Dao<StudentDTO, String> {

    private BasicConnectionPool connectionPool;

    Map<Integer, StudentDTO> studentDTOMap = new HashMap<>();

    private final  String insert = "INSERT INTO students (student_id, first_name, last_name,group_id) VALUES (DEFAULT, (?), (?), (?)) RETURNING id";

    public StudentDao(BasicConnectionPool pool) {
        this.connectionPool = pool;
    }


    @Override
    public boolean create(StudentDTO student) {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            ;
            statement.setString(1, student.getStudentFirstName());
            statement.setString(2, student.getStudentLastName());
            statement.setInt(3, student.getStudentGroupId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public StudentDTO read(String s) {
        return null;
    }

    @Override
    public boolean update(StudentDTO model) {
        return false;
    }

    @Override
    public boolean delete(StudentDTO model) {
        return false;
    }


}
