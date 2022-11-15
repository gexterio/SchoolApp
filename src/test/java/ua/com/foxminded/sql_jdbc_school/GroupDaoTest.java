package ua.com.foxminded.sql_jdbc_school;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.GroupDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.util.List;

class GroupDaoTest extends DataSourceDBUnit {
    GroupDao dao;

    @BeforeEach
    public void setUp() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/emptyDaoTest_data.xml"));
        super.setUp();
        connection = getConnection().getConnection();
        dao = new GroupDao(new BasicConnectionPool(props.getProperty("JDBC_URL"), props.getProperty("USER"), props.getProperty("PASSWORD")));
    }

    @Test
    void createShouldCreateNewGroupInDB() throws Exception {
        GroupDTO dto = new GroupDTO.GroupBuilder("99-ZZ").build();
        dao.create(dto);
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createGroupDaoTestCreate_data.xml"))
                .getTable("groups");
        ITable actualTable = getConnection().createDataSet().getTable("groups");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"group_id"});
    }

    @Test
    void createShouldThrowExceptionWhenInputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.create(null));
    }

    @Test
    void createShouldThrowExceptionInGroupDTOValidationWhenInputNameInvalid() {
        Assertions.assertThrows(Exception.class,
                () -> new GroupDTO.GroupBuilder("invalidName").build());
    }

    @Test
    void getAllShouldReturnListWithAllGroupsFromDB() throws Exception {
        dao.create(new GroupDTO.GroupBuilder("11-AA").build());
        dao.create(new GroupDTO.GroupBuilder("22-BB").build());
        dao.create(new GroupDTO.GroupBuilder("33-CC").build());
        List<GroupDTO> all = dao.getAll();
        int actualSize = all.size();
        int expectedSize = getConnection().createDataSet().getTable("groups").getRowCount();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllShouldReturnEmptyListWhenDBEmpty() {
        int actualSize = dao.getAll().size();
        assertEquals(0, actualSize);
    }

}
