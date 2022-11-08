package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;

import java.util.Map;
import java.util.Scanner;

public class SearchGroups implements UserOption {
    StudentDao studentDao;
    Scanner scanner;

    public SearchGroups(StudentDao studentDao, Scanner scanner) {
        this.studentDao = studentDao;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter students count");
        int count = Integer.parseInt(scanner.nextLine());
        System.out.println(searchGroups(count));
    }

    private Map<Integer, Integer> searchGroups(int studentCount) {
        return studentDao.searchGroupsByStudentCount(studentCount);
    }
}
