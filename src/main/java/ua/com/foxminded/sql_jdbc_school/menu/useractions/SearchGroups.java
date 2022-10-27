package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchGroups implements UserOption {
    StudentDao studentDao;
    Scanner scanner;

    public SearchGroups(StudentDao studentDao) {
        this.studentDao = studentDao;
        scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("Enter students count");
        int count = scanner.nextInt();
        System.out.println(searchGroups(count));
    }

    private Map<Integer, Integer> searchGroups(int studentCount) {
        List<StudentDTO> studentList = studentDao.getAll(); // TODO remove this line and searching by Dao
        Map<Integer, Integer> list = studentList.stream()
                .collect(Collectors.groupingBy(StudentDTO::getGroupId,
                        Collectors.summingInt(stud -> 1)));
        return list.entrySet().stream()
                .filter(entry -> entry.getValue() <= studentCount).skip(1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
