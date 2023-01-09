package ua.com.foxminded.sqljdbcschool.service_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateCourseDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateGroupDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateStudentDao;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;
import ua.com.foxminded.sqljdbcschool.dto.GroupDTO;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;
import ua.com.foxminded.sqljdbcschool.util.FileParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class SchoolDataGenerator {
    public static final Integer GROUPS_COUNT = 10;
    private static final Integer STUDENTS_COUNT = 200;
    StudentDao studentDao;
    GroupDao groupDao;
    CourseDao courseDao;
    Random random;
    FileParser parser;
    private Integer courseCount = 0;
    private Map<StudentDTO, CourseDTO> studentCoursesMap;


    @Autowired
    public SchoolDataGenerator(FileParser parser,
                               HibernateStudentDao studentDao,
                               HibernateGroupDao groupDao,
                               HibernateCourseDao courseDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.parser = parser;
    }


    public void generateSchoolData() {
        random = new Random();
        generateGroups();
        generateCourses(parser);
        generateStudents(parser, random);
        addStudentsToGroups();
        addStudentToCourses();
    }

    private void addStudentToCourses() {
        List<StudentDTO> studentList = studentDao.getAll();
        List<CourseDTO> courses = courseDao.getAll();
        studentCoursesMap = new HashMap<>();
        studentList.forEach(student -> addStudentToRandomCourse(courses, student));
        studentDao.batchAddStudentToCourse(studentCoursesMap);
    }

    private void addStudentToRandomCourse(List<CourseDTO> courses, StudentDTO student) {
        List<Integer> integers = new ArrayList<>(courseCount);
        for (int i = 0; i < courseCount; i++) {
            integers.add(i);
        }
        Collections.shuffle(integers);
        for (int j = 0; j < random.nextInt(3) + 1; j++) {
            studentCoursesMap.put(student, courses.get(integers.get(j)));
        }
    }

    private void addStudentsToGroups() {
        List<StudentDTO> studentDTOList = studentDao.getAll();
        List<StudentDTO> studentsWithGroupsList = new ArrayList<>();
        Collections.shuffle(studentDTOList);
        for (int i = 1; i <= GROUPS_COUNT; i++) {
            int chanceToEmptyGroup = random.nextInt(100);
            if (chanceToEmptyGroup > 70) {
                continue;
            }
            int limit = random.nextInt(20) + 10;
            if (limit >= studentDTOList.size()) {
                limit = studentDTOList.size() - 1;
            }
            for (int j = limit; j > 0; j--) {
                StudentDTO student = studentDTOList.get(j);
                studentsWithGroupsList.add(new StudentDTO.StudentBuilder(student.getFirstName(),
                        student.getLastName())
                        .setStudentId(student.getStudentId())
                        .setGroupId(i).build());
                studentDTOList.remove(student);
            }
        }
        studentDao.batchAddStudentToGroup(studentsWithGroupsList);
    }

    private void generateGroups() {
        List<GroupDTO> groups = new ArrayList<>();
        for (int i = 0; i < GROUPS_COUNT; i++) {
            String groupName = groupNameGenerator();
            groups.add(new GroupDTO.GroupBuilder(groupName).build());
        }
        groupDao.batchCreate(groups);
    }

    private void generateStudents(FileParser parser, Random random) {
        List<String> firstNames = parser.parseStudent("students_first_names");
        List<String> lastNames = parser.parseStudent("students_last_names");
        List<StudentDTO> students = new ArrayList<>();
        for (int i = 0; i < STUDENTS_COUNT; i++) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            students.add(new StudentDTO.StudentBuilder(firstName, lastName).build());
        }
        studentDao.batchCreate(students);
    }

    private void generateCourses(FileParser parser) {
        List<String> courses = parser.parseCourses("courses");
        List<CourseDTO> courseList = new ArrayList<>();
        for (String course : courses) {
            String[] line = course.split("_");
            courseList.add(new CourseDTO.CourseBuilder(line[0])
                    .setDescription(line[1])
                    .build());
            courseCount++;
        }
        courseDao.batchCreate(courseList);
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
