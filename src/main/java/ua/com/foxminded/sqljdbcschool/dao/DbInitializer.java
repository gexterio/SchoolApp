package ua.com.foxminded.sqljdbcschool.dao;

import java.util.List;

public interface DbInitializer {

    void initAllTables();

    void initTable(String sqlQuery);

    List<String> getSqlQuery();
}
