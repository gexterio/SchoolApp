package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SearchStudentsInCourse implements UserOption {
    CourseDao courseDao;
    Scanner scanner;

    public SearchStudentsInCourse(CourseDao courseDao) {
        this.courseDao = courseDao;
        scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("Enter course name");
        String courseName = scanner.nextLine();
        System.out.println(searchStudentsInCourse(courseName));
    }

    private List<Integer> searchStudentsInCourse(String courseName) {
        //TODO remove getAll and find by Dao
        Optional<CourseDTO> courseOptional = courseDao.getAll().stream().filter(entry -> entry.getCourseName().equalsIgnoreCase(courseName)).findFirst();
        CourseDTO course;
        if (courseOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid courseName");
        } else {
            course = courseOptional.get();
        }
        return courseDao.getAllStudentsInCourse(course);
    }
}
