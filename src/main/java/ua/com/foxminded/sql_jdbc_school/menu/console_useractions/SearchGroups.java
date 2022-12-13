package ua.com.foxminded.sql_jdbc_school.menu.console_useractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sql_jdbc_school.dao.jdbc_template.JDBCTemplateStudentDao;

import java.util.Map;
import java.util.Scanner;

@Component
public class SearchGroups implements UserOption {
    JDBCTemplateStudentDao studentDao;

    @Autowired
    public SearchGroups(JDBCTemplateStudentDao studentDao) {
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
            throw new IllegalArgumentException("input must be number");
        }
        System.out.println(searchGroups(count));
    }

    private Map<Integer, Integer> searchGroups(int studentCount) {
        return studentDao.searchGroupsByStudentCount(studentCount);
    }
}
