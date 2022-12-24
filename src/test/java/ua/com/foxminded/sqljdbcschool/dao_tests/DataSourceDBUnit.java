package ua.com.foxminded.sqljdbcschool.dao_tests;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
//import ua.com.foxminded.sqljdbcschool.TestSpringConfig;
import ua.com.foxminded.sqljdbcschool.util.FileReader;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

@Component
class DataSourceDBUnit extends DataSourceBasedDBTestCase {
    public final static String TEST_DB = "testDBProperties.properties";
    public final Properties props = new Properties();
    Connection connection;
    IDataSet dataSet;


    @BeforeEach
    public void setup() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestSpringConfig.class);

    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected DataSource getDataSource() {
        try {
            props.load(new FileReader().readProperties(TEST_DB));
        } catch (IOException e) {
            e.printStackTrace();

        }
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(props.getProperty("JDBC_SET_UP_URL"));
        dataSource.setUser(props.getProperty("USER"));
        dataSource.setPassword(props.getProperty("PASSWORD"));
        return dataSource;
    }


    @Override
    protected IDataSet getDataSet() {
        return dataSet;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.CLEAN_INSERT;
    }
}