package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.PersonalCoursesDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

public class AddStudentToCourse implements UserOption {
    PersonalCoursesDao personalCoursesDao;

    public AddStudentToCourse(PersonalCoursesDao personalCoursesDao) {
        this.personalCoursesDao = personalCoursesDao;
    }

  public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        personalCoursesDao.addStudentToCourse(student, course);
    }
}
