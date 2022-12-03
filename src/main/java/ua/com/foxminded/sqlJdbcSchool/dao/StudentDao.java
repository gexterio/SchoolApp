package ua.com.foxminded.sqlJdbcSchool.dao;

import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.util.List;
import java.util.Map;

public interface StudentDao {

     void create (StudentDTO student);

     List<StudentDTO> getAll();

     StudentDTO searchById(Integer id);

     void delete (StudentDTO student);

     void addStudentToGroup(StudentDTO student, Integer groupId);

     void addStudentToCourse(StudentDTO student, CourseDTO course);

     void deleteStudentFromCourse(StudentDTO student, CourseDTO course);

     Map<Integer, Integer> searchGroupsByStudentCount(Integer studentCount);

}