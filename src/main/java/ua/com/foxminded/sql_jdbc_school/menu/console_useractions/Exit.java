package ua.com.foxminded.sql_jdbc_school.menu.console_useractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;

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
