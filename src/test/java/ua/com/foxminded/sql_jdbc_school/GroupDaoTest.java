package ua.com.foxminded.sql_jdbc_school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.sql_jdbc_school.dao.GroupDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.util.List;

 class GroupDaoTest extends DataSourceDBUnitTest {
    GroupDao dao;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        dao = new GroupDao(new BasicConnectionPool(props.getProperty("JDBC_URL"), props.getProperty("USER"), props.getProperty("PASSWORD")));
    }

    @Test
     void createShouldCreateNewGroupsInDBWithInputData() {
        GroupDTO dto = new GroupDTO.GroupBuilder("99-ZZ").setId(6).build();
        dao.create(dto);
        List<GroupDTO> list = dao.getAll();
        String expected = dto.toString();
        String actual = list.get(list.size() - 1).toString();
        assertEquals(expected, actual);
    }

    @Test
     void getAllShouldReturnAllGroupsFromDB() {
        List<GroupDTO> all = dao.getAll();
        int expectedSize = 5;
        int actualSize = all.size();
        assertEquals(expectedSize, actualSize);
    }
}
