package ua.com.foxminded.sql_jdbc_school;

import org.dbunit.Assertion;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.GroupDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.io.InputStream;
import java.util.List;

class GroupDaoTest extends DataSourceDBUnit {
    GroupDao dao;

    @BeforeEach
    public void setUp() throws Exception {
        dataSource = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/emptyGroupDaoTest_data.xml"));
        super.setUp();
        connection = getConnection().getConnection();
        dao = new GroupDao(new BasicConnectionPool(props.getProperty("JDBC_URL"), props.getProperty("USER"), props.getProperty("PASSWORD")));
    }


    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();

    }

    @Test
    void getAllShouldReturnAllGroupsFromDB() throws Exception {
        dao.create(new GroupDTO.GroupBuilder("11-AA").build());
        dao.create(new GroupDTO.GroupBuilder("22-BB").build());
        dao.create(new GroupDTO.GroupBuilder("33-CC").build());
        List<GroupDTO> all = dao.getAll();
        int expectedSize = getConnection().createDataSet().getTable("groups").getRowCount();
        System.out.println("expectedSize = " + expectedSize);
        int actualSize = all.size();
        System.out.println("actualSize = " + actualSize);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createShouldCreateNewGroupsInDBWithInputData() throws Exception {
        GroupDTO dto = new GroupDTO.GroupBuilder("99-ZZ").build();
        dao.create(dto);
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/after_groupDaoTestCreate_data.xml"))
                .getTable("groups");
        ITable actualTable = getConnection().createDataSet().getTable("groups");
        Assertion.assertEquals(expectedTable, actualTable);
    }
}
