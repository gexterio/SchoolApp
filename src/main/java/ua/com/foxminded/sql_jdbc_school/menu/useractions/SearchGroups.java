package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchGroups implements UserOption {
    StudentDao studentDao;

    public SearchGroups(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Map<Integer, Integer> searchGroups(int studentCount) {
        List<StudentDTO> studentList = studentDao.getAll();
        Map<Integer, Integer> list = studentList.stream()
                .collect(Collectors.groupingBy(StudentDTO::getGroupId,
                        Collectors.summingInt(stud -> 1)));
        return list.entrySet().stream()
                .filter(entry -> entry.getValue() <= studentCount)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
