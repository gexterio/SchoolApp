package ua.com.foxminded.sqljdbcschool.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentDao {

    void create(StudentDTO student);

    List<StudentDTO> getAll();

    StudentDTO searchById(Integer id);

    void delete(StudentDTO student);

    void addStudentToGroup(StudentDTO student, Integer groupId);

    void addStudentToCourse(StudentDTO student, CourseDTO course);

    void deleteStudentFromCourse(StudentDTO student, CourseDTO course);

    Map<Integer, Integer> searchGroupsByStudentCount(Integer studentCount);

    void batchCreate(List<StudentDTO> students);

    void batchAddStudentToGroup(List<StudentDTO> students);

    void batchAddStudentToCourse(Map<StudentDTO, CourseDTO> studentCoursesMap);
}
