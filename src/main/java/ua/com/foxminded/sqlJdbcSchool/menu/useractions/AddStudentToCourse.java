package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import ua.com.foxminded.sqlJdbcSchool.dao.CourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;

import java.util.Scanner;

public class AddStudentToCourse implements UserOption {
    StudentDao studentDao;
    CourseDao courseDao;


    public AddStudentToCourse(StudentDao studentDao, CourseDao courseDao) {
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
