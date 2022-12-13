package ua.com.foxminded.sqljdbcschool.menu.consoleuseractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.jdbc_template.JDBCTemplateCourseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class SearchStudentsInCourse implements UserOption {
    CourseDao courseDao;

    @Autowired
    public SearchStudentsInCourse(JDBCTemplateCourseDao courseDao) {
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
