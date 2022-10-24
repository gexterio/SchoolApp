package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

import java.util.List;
import java.util.Optional;

public class SearchStudentsInCourse implements UserOption {
    CourseDao courseDao;

    public SearchStudentsInCourse(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public List<Integer> searchStudentsInCourse(String courseName) {
        Optional<CourseDTO> courseOptional = courseDao.getAll().stream().filter(entry -> entry.getCourseName().equalsIgnoreCase(courseName)).findFirst();
        CourseDTO course;
        if (courseOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid courseName");
        } else {
            course = courseOptional.get();
        }
        return courseDao.getAllStudentsInCourse(course);
    }
}
