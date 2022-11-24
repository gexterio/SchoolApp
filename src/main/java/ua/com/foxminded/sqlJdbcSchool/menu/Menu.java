package ua.com.foxminded.sqlJdbcSchool.menu;

import ua.com.foxminded.sqlJdbcSchool.dao.CourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;
import ua.com.foxminded.sqlJdbcSchool.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.AddStudent;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.AddStudentToCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.DeleteStudent;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.Exit;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.RemoveStudentFromCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.SearchGroups;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.SearchStudentsInCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.UserOption;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Menu {
    private final Map<Integer, UserOption> userOptions;

    public Menu(BasicConnectionPool basicConnectionPool, StudentDao studentDao, CourseDao courseDao) {
        userOptions = new HashMap<>();
        addOptions(basicConnectionPool, studentDao, courseDao);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Choose any option");
            int i = scanner.nextInt();
            userOptions.get(i).execute();
            if (i == 6) {
                break;
            }
        }

    }

    private void addOptions(BasicConnectionPool basicConnectionPool, StudentDao studentDao, CourseDao courseDao) {
        userOptions.put(0, new AddStudent(studentDao));
        userOptions.put(1, new AddStudentToCourse(studentDao, courseDao));
        userOptions.put(2, new DeleteStudent(studentDao));
        userOptions.put(3, new RemoveStudentFromCourse(studentDao, courseDao));
        userOptions.put(4, new SearchGroups(studentDao));
        userOptions.put(5, new SearchStudentsInCourse(courseDao));
        userOptions.put(6, new Exit(basicConnectionPool));
    }

    private void printMenu() {
        System.out.println("—".repeat(30));
        IntStream.range(0, userOptions.size()).forEach(
                index -> System.out.printf("[%d] — %s\n", index, ((userOptions.get(index).getClass().getSimpleName()))));
        System.out.println("—".repeat(30));
    }
}
