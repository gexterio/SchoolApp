package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;

public class Exit implements UserOption {
    BasicConnectionPool basicConnectionPool;

    public Exit(BasicConnectionPool basicConnectionPool) {
        this.basicConnectionPool = basicConnectionPool;
    }
@Override
    public void execute  () {
        basicConnectionPool.closePoolConnection();
    }
}
