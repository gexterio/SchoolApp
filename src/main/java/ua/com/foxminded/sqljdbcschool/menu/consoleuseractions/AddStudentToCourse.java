package ua.com.foxminded.sqljdbcschool.menu.consoleuseractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.dao.jdbc_template.JDBCTemplateCourseDao;
import ua.com.foxminded.sqljdbcschool.dao.jdbc_template.JDBCTemplateStudentDao;

import java.util.Scanner;

@Component
public class AddStudentToCourse implements UserOption {
    StudentDao studentDao;
    CourseDao courseDao;

    @Autowired
    public AddStudentToCourse(JDBCTemplateStudentDao studentDao, JDBCTemplateCourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;

    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id");
        int studentId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter course id");
        int courseId = Integer.parseInt(scanner.nextLine());
        addStudentToCourse(studentId, courseId);
    }

    private void addStudentToCourse(int studentId, int courseId) {
        studentDao.addStudentToCourse(
                studentDao.searchById(studentId),
                courseDao.searchById(courseId));
    }
}