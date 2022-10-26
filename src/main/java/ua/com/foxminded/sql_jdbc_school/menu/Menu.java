package ua.com.foxminded.sql_jdbc_school.menu;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;
import ua.com.foxminded.sql_jdbc_school.menu.useractions.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Menu {
    BasicConnectionPool basicConnectionPool;
    StudentDao studentDao;
    CourseDao courseDao;
    public AddStudent addStudent;
    public AddStudentToCourse addStudentToCourse;
    public DeleteStudent deleteStudent;
    public RemoveStudentFromCourse removeStudentFromCourse;
    public SearchGroups searchGroups;
    public SearchStudentsInCourse searchStudentsInCourse;
    public Exit exit;

    public Menu(BasicConnectionPool basicConnectionPool, StudentDao studentDao, CourseDao courseDao
    ) {
        this.basicConnectionPool = basicConnectionPool;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.addStudent = new AddStudent(studentDao);
        this.addStudentToCourse = new AddStudentToCourse(studentDao);
        this.deleteStudent = new DeleteStudent(studentDao);
        this.exit = new Exit(basicConnectionPool);
        this.removeStudentFromCourse = new RemoveStudentFromCourse(studentDao);
        this.searchGroups = new SearchGroups(studentDao);
        this.searchStudentsInCourse = new SearchStudentsInCourse(courseDao);
    }

    public void run() {
        printMenu();
        launchMenu();
    }

    private void launchMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the action");
        int input = scanner.nextInt();
        switch (input) {
            case 0:
                scanner.nextLine();
                System.out.println("Enter First Name");
                System.out.println("Enter Last Name");
                addStudent.addStudent(new StudentDTO.StudentBuilder(scanner.nextLine(), scanner.nextLine()).build());
                run();
                break;
            case 1:
                scanner.nextLine();
                System.out.println("Enter student id");
                System.out.println("Enter course id");
                addStudentToCourse.addStudentToCourse(
                        studentDao.searchById(scanner.nextInt()),
                        courseDao.searchById(scanner.nextInt()));
                run();
                break;
            case 2:
                System.out.println("Enter student id");
                deleteStudent.deleteStudent(studentDao.searchById(scanner.nextInt()));
                run();
                break;
            case 3:
                System.out.println("Enter student id");
                System.out.println("Enter course id");
                removeStudentFromCourse.removeStudentFromCourse(
                        studentDao.searchById(scanner.nextInt()),
                        courseDao.searchById(scanner.nextInt()));
                run();
                break;
            case 4:
                System.out.println("Enter students count");
                System.out.println(searchGroups.searchGroups(scanner.nextInt()));
                run();
                break;
            case 5:
                System.out.println("Enter course name");
                scanner.nextLine();
                String courseName = scanner.nextLine();
                System.out.println(searchStudentsInCourse.searchStudentsInCourse(courseName));
                run();
                break;
            case 6:
                exit.exit();
                break;
        }

    }

    private void printMenu() {
        System.out.println("—".repeat(30));
        List<Field> fields = Arrays.stream(Menu.class.getDeclaredFields()).skip(3).collect(Collectors.toList());
        IntStream.range(0, fields.size()).forEach(
                index -> System.out.printf("[%d] — %s\n", index, ((fields.get(index).getName()))));
        System.out.println("—".repeat(30));
    }
}
