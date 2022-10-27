package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;

import java.util.Scanner;

public class RemoveStudentFromCourse implements UserOption {
    StudentDao studentDao;
    CourseDao courseDao;
    Scanner scanner;

    public RemoveStudentFromCourse(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("Enter student id");
        int studentId = scanner.nextInt();
        System.out.println("Enter course id");
        int courseId = scanner.nextInt();
        removeStudentFromCourse(studentId, courseId);
    }

    private void removeStudentFromCourse(int studentId, int courseId) {
        studentDao.deleteStudentFromCourse(
                studentDao.searchById(studentId),
                courseDao.searchById(courseId));
    }
}
