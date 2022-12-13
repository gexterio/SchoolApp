package ua.com.foxminded.sql_jdbc_school.dao;

import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;

import java.util.List;

public interface CourseDao {

    void create(CourseDTO course);

    List<CourseDTO> getAll();

    CourseDTO searchById(int id);

    CourseDTO searchByName(String name);

    List<Integer> searchStudentsInCourse(String courseName);
}
