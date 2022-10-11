package ua.com.foxminded.sql_jdbc_school.servicedb;


import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.GroupDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;

import java.util.Random;

public class SchoolDataGenerator {
    BasicConnectionPool connectionPool;
    StudentDao studentDao;
    GroupDao groupDao;
    CourseDao courseDao;
    Random random;

    public SchoolDataGenerator(BasicConnectionPool connectionPool, StudentDao studentDao, GroupDao groupDao, CourseDao courseDao) {
        this.connectionPool = connectionPool;
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        random = new Random();
    }

    public void generateSchoolData() {
        generateGroups();
        generateCourses();
        generateStudents();
        for (int i = 0; i < 10; i++) {

            groupNameGenerator();
        }
    }

    private void generateGroups() {
        for (int i = 0; i < 10; i++) {
            String group_name = groupNameGenerator();
            groupDao.create(new GroupDTO(1, group_name));
        }
    }

    private void generateStudents() {
    }

    private void generateCourses() {
    }

    private String groupNameGenerator() {
        String groupNamePattern = "^\\d{2}+-+[A-Z]{2}";
        StringBuilder builder = new StringBuilder();
        builder.append(random.nextInt(10));
        builder.append(random.nextInt(10));
        builder.append("-");
        builder.append((char) ('A' + random.nextInt(26)));
        builder.append((char) ('A' + random.nextInt(26)));
        if (builder.toString().matches(groupNamePattern)) {
            return builder.toString();
        }
        else throw new IllegalArgumentException("Generated Invalid group name");
    }
}
