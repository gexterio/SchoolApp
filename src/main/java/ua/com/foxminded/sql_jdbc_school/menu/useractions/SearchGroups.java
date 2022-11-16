package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;

import java.util.Map;
import java.util.Scanner;

public class SearchGroups implements UserOption {
    StudentDao studentDao;

    public SearchGroups(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter students count");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("input must be number");
        }
        System.out.println(searchGroups(count));
    }

    private Map<Integer, Integer> searchGroups(int studentCount) {
        return studentDao.searchGroupsByStudentCount(studentCount);
    }
}
