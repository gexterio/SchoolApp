package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;

import java.sql.SQLException;

public class DBInitializer {

    public static BasicConnectionPool initDB (String url, String user, String password) throws SQLException {
        try {
            return BasicConnectionPool.create(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("can't initialize DB");
    }
    }

