package ua.com.foxminded.sql_jdbc_school.servicedb;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.GroupDao;
import ua.com.foxminded.sql_jdbc_school.dao.PersonalCoursesDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.GroupDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;
import ua.com.foxminded.sql_jdbc_school.util.FileParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SchoolDataGenerator {
    BasicConnectionPool connectionPool;
    StudentDao studentDao;
    GroupDao groupDao;
    CourseDao courseDao;
    PersonalCoursesDao personalCoursesDao;
    Random random;
    FileParser parser;
    private static final Integer GROUPS_COUNT = 10;
    private static final Integer STUDENTS_COUNT = 200;
    private Integer courseCount = 0;

    public SchoolDataGenerator(BasicConnectionPool connectionPool, StudentDao studentDao, GroupDao groupDao,
                               CourseDao courseDao, PersonalCoursesDao personalCoursesDao) {
        this.connectionPool = connectionPool;
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.personalCoursesDao = personalCoursesDao;
        random = new Random();
    }

    public void generateSchoolData() {
        generateGroups();
        generateCourses();
        generateStudents();
        addStudentsToGroups();
        addStudentToCourses();
    }

    private void addStudentToCourses() {
        List<StudentDTO> studentList = studentDao.getAll();
        List<CourseDTO> courses = courseDao.getAll();
        studentList.forEach(student -> addStudentToRandomCourse(courses, student));
    }

    private void addStudentToRandomCourse(List<CourseDTO> courses, StudentDTO student) {
        List<Integer> integers = new ArrayList<>(courseCount);
        for (int i = 0; i < courseCount; i++) {
            integers.add(i);
        }
        Collections.shuffle(integers);
        for (int j = 0; j < random.nextInt(3) + 1; j++) {
            personalCoursesDao.addStudentToCourse(student, courses.get(integers.get(j)));
        }
    }

    private void addStudentsToGroups() {
        List<StudentDTO> studentDTOList = studentDao.getAll();
        Collections.shuffle(studentDTOList);
        for (int i = 1; i <= GROUPS_COUNT; i++) {
            int chanceToEmptyGroup = random.nextInt(100);
            if (chanceToEmptyGroup > 90) {
                continue;
            }
            int limit = random.nextInt(20) + 10;
            if (limit >= studentDTOList.size()) {
                limit = studentDTOList.size() - 1;
            }
            for (int j = limit; j > 0; j--) {
                StudentDTO student = studentDTOList.get(j);
                studentDao.addStudentToGroup(student, i);
                studentDTOList.remove(student);
            }

        }

    }

    private void generateGroups() {
        for (int i = 0; i < GROUPS_COUNT; i++) {
            String groupName = groupNameGenerator();
            groupDao.create(new GroupDTO.GroupBuilder(groupName).setId(i).build());
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
            studentDao.create(new StudentDTO.StudentBuilder(firstName, lastName).setStudentId(i).build());
        }
    }

    private void generateCourses() {
        parser = new FileParser();
        List<String> courses = parser.parseCourses("courses");
        for (int i = 0; i < courses.size(); i++) {
            String[] line = courses.get(i).split("_");
            courseDao.create(new CourseDTO.CourseBuilder(line[0])
                    .setId(i)
                    .setDescription(line[1])
                    .build());
            courseCount++;
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
