package ua.com.foxminded.sqlJdbcSchool.daoTests;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateGroupDao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.Mappers.GroupMapper;
import ua.com.foxminded.sqlJdbcSchool.dto.GroupDTO;
import ua.com.foxminded.sqlJdbcSchool.util.DTOInputValidator;

class GroupDaoTest extends DataSourceDBUnit {
    JDBCTemplateGroupDao dao;

    @BeforeEach
    public void setUp() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/emptyDaoTest_data.xml"));
        super.setUp();
        connection = getConnection().getConnection();
        dao = new JDBCTemplateGroupDao(jdbcTemplate, new DTOInputValidator(), new GroupMapper());
    }

    @Test
    void create_createNewGroupInDB_inputIsValid() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createGroupDaoTest_data.xml"))
                .getTable("groups");
        GroupDTO dto = new GroupDTO.GroupBuilder("99-ZZ").build();
        dao.create(dto);
        ITable actualTable = getConnection().createDataSet().getTable("groups");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"group_id"});
    }

    @Test
    void create_thrownIllegalArgumentException_inputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.create(null));
    }

    @Test
    void create_thrownException_inputIsInvalid() {
        Assertions.assertThrows(Exception.class,
                () -> new GroupDTO.GroupBuilder("invalidName").build());
    }

    @Test
    void getAll_returnedListWithAllGroupsFromDB() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createGroupDaoTest_data.xml"));
        super.setUp();
        int expectedSize = getConnection().createDataSet().getTable("groups").getRowCount();
        int actualSize = dao.getAll().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAll_returnedEmptyList_dBIsEmpty() {
        int actualSize = dao.getAll().size();
        assertEquals(0, actualSize);
    }

}
