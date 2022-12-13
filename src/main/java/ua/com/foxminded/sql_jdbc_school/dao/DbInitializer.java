package ua.com.foxminded.sql_jdbc_school.dao;

import java.util.List;

public interface DbInitializer {

    void initAllTables();

    void initTable(String sqlQuery);

    List<String> getSqlQuery();
}
