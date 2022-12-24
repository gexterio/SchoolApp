package ua.com.foxminded.sqljdbcschool.dao;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;

import java.util.List;

public interface CourseDao {

    void create(CourseDTO course);

    List<CourseDTO> getAll();

    CourseDTO searchById(int id);

    CourseDTO searchByName(String name);

    List<Integer> searchStudentsInCourse(String courseName);

    @Transactional
    void batchCreate(List<CourseDTO> courses);
}
