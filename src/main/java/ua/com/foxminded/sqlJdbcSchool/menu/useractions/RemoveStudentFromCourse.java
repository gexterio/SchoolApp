package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateCourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;

import java.util.Scanner;

@Component
public class RemoveStudentFromCourse implements UserOption {
    JDBCTemplateStudentDao studentDao;
    JDBCTemplateCourseDao courseDao;

    @Autowired
    public RemoveStudentFromCourse(JDBCTemplateStudentDao studentDao, JDBCTemplateCourseDao courseDao) {
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
        removeStudentFromCourse(studentId, courseId);
    }

    private void removeStudentFromCourse(int studentId, int courseId) {
        studentDao.deleteStudentFromCourse(
                studentDao.searchById(studentId),
                courseDao.searchById(courseId));
    }
}
