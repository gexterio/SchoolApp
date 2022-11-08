package ua.com.foxminded.sql_jdbc_school;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.util.FileReader;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {
    public final static String TEST_DB = "testDBProperties.properties";
    Connection connection;
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
    protected IDataSet getDataSet() throws Exception {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("data.xml")) {
            return new FlatXmlDataSetBuilder().build(resourceAsStream);
        }
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        connection = getConnection().getConnection();
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    void isTablesCreated() throws Exception {
        IDataSet dataSet = getConnection().createDataSet();
        assertNotNull(dataSet);
    }

}