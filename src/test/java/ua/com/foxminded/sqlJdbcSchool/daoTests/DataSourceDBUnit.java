package ua.com.foxminded.sqlJdbcSchool.daoTests;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import ua.com.foxminded.sqlJdbcSchool.util.FileReader;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

class DataSourceDBUnit extends DataSourceBasedDBTestCase {
    public final static String TEST_DB = "testDBProperties.properties";
    Connection connection;
    IDataSet dataSet;
    public final Properties props = new Properties();

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