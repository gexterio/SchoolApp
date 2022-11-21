package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;

public class Exit implements UserOption {
    BasicConnectionPool basicConnectionPool;

    public Exit(BasicConnectionPool basicConnectionPool) {
        this.basicConnectionPool = basicConnectionPool;
    }

    @Override
    public void execute() {
        exit();
    }

    private void exit() {
        basicConnectionPool.closePoolConnection();
    }
}
