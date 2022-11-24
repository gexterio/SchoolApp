package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import ua.com.foxminded.sqlJdbcSchool.dao.CourseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchStudentsInCourse implements UserOption {
    CourseDao courseDao;

    public SearchStudentsInCourse(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter course name");
        String courseName = scanner.nextLine();
        if (courseName.isEmpty() || courseName.isBlank()) {
            throw new IllegalArgumentException("course name can't be Empty or Blank");
        }
        System.out.println(searchStudentsInCourse(courseName));
    }

    private List<Integer> searchStudentsInCourse(String courseName) {
        return new ArrayList<>(courseDao.searchStudentsInCourse(courseName));
    }
}
