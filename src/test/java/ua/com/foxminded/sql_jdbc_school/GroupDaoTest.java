package ua.com.foxminded.sql_jdbc_school;

import org.dbunit.Assertion;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
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
        super.setUp();
        dao = new GroupDao(new BasicConnectionPool(props.getProperty("JDBC_URL"), props.getProperty("USER"), props.getProperty("PASSWORD")));
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("beforeData/before_groupDaoTest_data.xml");
        beforeData = new FlatXmlDataSetBuilder().build(resourceAsStream);
    }

    @Test
    void createShouldCreateNewGroupsInDBWithInputData() throws Exception {
        GroupDTO dto = new GroupDTO.GroupBuilder("99-ZZ").setId(1).build();
        dao.create(dto);
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/after_groupDaoTestCreate_data.xml"))
                .getTable("groups");
        ITable actualTable = getConnection().createDataSet().getTable("groups");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"group_id"});
    }

    @Test
    void getAllShouldReturnAllGroupsFromDB() throws Exception {
        beforeData = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("data.xml"));
        List<GroupDTO> all = dao.getAll();
        int expectedSize = beforeData.getTable("groups").getRowCount();
        int actualSize = all.size();
        assertEquals(expectedSize, actualSize);
    }
}
