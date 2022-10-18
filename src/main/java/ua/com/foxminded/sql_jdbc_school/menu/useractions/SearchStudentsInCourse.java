package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.PersonalCoursesDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.PersonalCoursesDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchStudentsInCourse implements UserOption {
    PersonalCoursesDao personalCoursesDao;
    CourseDao courseDao;

    public SearchStudentsInCourse(PersonalCoursesDao personalCoursesDao, CourseDao courseDao) {
        this.personalCoursesDao = personalCoursesDao;
        this.courseDao = courseDao;
    }

    public List<Integer> searchStudentsInCourse(String courseName) {
        Optional<CourseDTO> courseDTO = courseDao.getAll().stream().filter(item -> item.getCourseName().equalsIgnoreCase(courseName)).findFirst();
        if (courseDTO.isEmpty()) {
            return Collections.emptyList();
        }
        Integer courseID = courseDTO.get().getCourseId();
        List<PersonalCoursesDTO> list = personalCoursesDao.getAll();
        return list.stream()
                .filter(item -> item.getCourseId().equals(courseID))
                .map(PersonalCoursesDTO::getStudentId)
                .collect(Collectors.toList());
    }
}
