package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.PersonalCoursesDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

public class AddStudentToCourse implements UserOption {
    StudentDao studentDao;

    public AddStudentToCourse(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        studentDao.addStudentToCourse(student, course);
    }
}
