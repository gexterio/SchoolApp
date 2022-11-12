package ua.com.foxminded.sql_jdbc_school;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import ua.com.foxminded.sql_jdbc_school.util.FileReader;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

class DataSourceDBUnit extends DataSourceBasedDBTestCase {
    public final static String TEST_DB = "testDBProperties.properties";
    Connection connection;
    IDataSet dataSource;
    public final Properties props = new Properties();

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
        return dataSource;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return super.getTearDownOperation();
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return super.getSetUpOperation();
    }
}