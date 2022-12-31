package ua.com.foxminded.sqlJdbcSchool.dao;

import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;

import java.util.List;

public interface CourseDao {

    void create(CourseDTO course);

    List<CourseDTO> getAll();

    CourseDTO searchById(int id);

    CourseDTO searchByName(String name);

    List<Integer> searchStudentsInCourse(String courseName);
}
