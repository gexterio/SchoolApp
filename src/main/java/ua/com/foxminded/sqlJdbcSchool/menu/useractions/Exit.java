package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;

@Component
public class Exit implements UserOption {
    BasicConnectionPool basicConnectionPool;

    @Autowired
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
