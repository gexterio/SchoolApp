package ua.com.foxminded.sqljdbcschool.menu.consoleuseractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqljdbcschool.dao.connection.ConnectionPool;

@Component
public class Exit implements UserOption {
    ConnectionPool basicConnectionPool;

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
