package ua.com.foxminded.sqljdbcschool.dao_tests;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.com.foxminded.sqljdbcschool.TestSpringConfig;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateGroupDao;
import ua.com.foxminded.sqljdbcschool.dto.GroupDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringConfig.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupDaoTest extends DataSourceDBUnit {
    @Autowired
    ApplicationContext context;
    GroupDao groupDao;

    @BeforeAll
    public void setUpBeans() {
        groupDao = context.getBean("hibernateGroupDao", HibernateGroupDao.class);
    }

    @BeforeEach
    public void setUp() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("beforeData/emptyDaoTest_data.xml"));
        super.setUp();
        connection = getConnection().getConnection();
    }

    @Test
    void create_createNewGroupInDB_inputIsValid() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader()
                        .getResourceAsStream("afterData/createGroupDaoTest_data.xml"))
                .getTable("groups");
        GroupDTO dto = new GroupDTO.GroupBuilder("99-ZZ").build();
        groupDao.create(dto);
        ITable actualTable = getConnection().createDataSet().getTable("groups");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, new String[]{"group_id"});
    }

    @Test
    void create_thrownIllegalArgumentException_inputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupDao.create(null));
    }

    @Test
    void create_thrownException_inputIsInvalid() {
        GroupDTO inputGroup = new GroupDTO.GroupBuilder("invalidName").build();
        Assertions.assertThrows(Exception.class,
                () -> groupDao.create(inputGroup));
    }

    @Test
    void getAll_returnedListWithAllGroupsFromDB() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("afterData/createGroupDaoTest_data.xml"));
        super.setUp();
        int expectedSize = getConnection().createDataSet().getTable("groups").getRowCount();
        int actualSize = groupDao.getAll().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAll_returnedEmptyList_dBIsEmpty() {
        int actualSize = groupDao.getAll().size();
        assertEquals(0, actualSize);
    }

}
