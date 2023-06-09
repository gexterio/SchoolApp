package ua.com.foxminded.sqlJdbcSchool.dao.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicConnectionPool implements ConnectionPool {

    private final String url;
    private final String user;
    private final String password;
    private List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 10;

    public BasicConnectionPool(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            this.connectionPool = initConnectionPool(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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

    private List<Connection> initConnectionPool(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return pool;
    }

    private static Connection createConnection(String url, String user, String password)
            throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    private static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}