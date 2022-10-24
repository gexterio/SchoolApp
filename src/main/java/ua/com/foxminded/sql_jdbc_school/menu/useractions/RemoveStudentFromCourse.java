package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

public class RemoveStudentFromCourse implements UserOption {
    StudentDao studentDao;

    public RemoveStudentFromCourse(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void removeStudentFromCourse(StudentDTO student, CourseDTO course) {
        studentDao.deleteStudentFromCourse(student, course);
    }
}
