package ua.com.foxminded.sql_jdbc_school.servicedb;


import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.GroupDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;
import ua.com.foxminded.sql_jdbc_school.util.FileParser;

import java.util.List;
import java.util.Random;

public class SchoolDataGenerator {
    BasicConnectionPool connectionPool;
    StudentDao studentDao;
    GroupDao groupDao;
    CourseDao courseDao;
    Random random;
    FileParser parser;
    private static final Integer GROUPS_COUNT = 10;
    private static final Integer STUDENTS_COUNT = 200;

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
        addStudentsToCourses();
    }

    private void addStudentsToCourses() {

    }

    private void generateGroups() {
        for (int i = 0; i < GROUPS_COUNT; i++) {
            String groupName = groupNameGenerator();
            groupDao.create(new GroupDTO(i, groupName));
        }
    }

    private void generateStudents() {
        parser = new FileParser();
        random = new Random();
        List<String> firstNames = parser.parseStudent("students_first_names");
        List<String> lastNames = parser.parseStudent("students_last_names");
        for (int i = 0; i < STUDENTS_COUNT; i++) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            studentDao.create(new StudentDTO(i, firstName, lastName, random.nextInt(GROUPS_COUNT) + 1));
        }
    }

    private void generateCourses() {
        parser = new FileParser();
        List<String> courses = parser.parseCourses("courses");
        for (int i = 0; i < courses.size(); i++) {
            String[] line = courses.get(i).split("_");
            courseDao.create(new CourseDTO(i, line[0], line[1]));
        }

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
        } else throw new IllegalArgumentException("Generated Invalid group name");
    }
}
