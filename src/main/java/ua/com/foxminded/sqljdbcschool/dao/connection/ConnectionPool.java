package ua.com.foxminded.sqljdbcschool.dao.connection;


import org.springframework.stereotype.Component;

import java.sql.Connection;
public interface ConnectionPool {
    Connection getConnection();

    void releaseConnection(Connection connection);

    void closePoolConnection();
}
