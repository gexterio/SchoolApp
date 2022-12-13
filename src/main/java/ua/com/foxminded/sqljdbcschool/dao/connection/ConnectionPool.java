package ua.com.foxminded.sqljdbcschool.dao.connection;


import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    void releaseConnection(Connection connection);

    void closePoolConnection();
}
