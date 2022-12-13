package ua.com.foxminded.sql_jdbc_school.dao.connection;


import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    void releaseConnection(Connection connection);

    void closePoolConnection();
}
