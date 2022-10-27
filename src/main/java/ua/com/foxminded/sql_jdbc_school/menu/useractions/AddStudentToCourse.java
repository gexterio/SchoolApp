package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;

import java.util.Scanner;

public class AddStudentToCourse implements UserOption {
    StudentDao studentDao;
    CourseDao courseDao;
    Scanner scanner;

    public AddStudentToCourse(StudentDao studentDao, CourseDao courseDao) {
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
        addStudentToCourse(studentId, courseId);
    }

    private void addStudentToCourse(int studentId, int courseId) {
        studentDao.addStudentToCourse(
                studentDao.searchById(studentId),
                courseDao.searchById(courseId));
    }
}
