package ua.com.foxminded.sqlJdbcSchool.dao.connection;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class BasicConnectionPool implements ConnectionPool {

    private static final int INITIAL_POOL_SIZE = 10;
    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> usedConnections = new ArrayList<>();
    private List<Connection> connectionPool;

    public BasicConnectionPool(@Value("${url}") String url,
                               @Value("${user}") String user,
                               @Value("${password}") String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        setUp();
    }

    public void setUp() {
        try {
            this.connectionPool = initConnectionPool(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void closePoolConnection() {
        for (Connection connection : connectionPool) {
            closeConnection(connection);
        }
        for (Connection usedConnection : usedConnections) {
            closeConnection(usedConnection);
        }
    }

    @Override
    public Connection getConnection() {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    private Connection createConnection(String url, String user, String password)
            throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Connection> initConnectionPool(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return pool;
    }


}