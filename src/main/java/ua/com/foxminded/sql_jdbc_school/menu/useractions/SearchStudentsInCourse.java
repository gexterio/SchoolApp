package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;

import java.util.ArrayList;
import java.util.List;
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
        return new ArrayList<>(courseDao.searchStudentsInCourse(courseName));
    }
}
