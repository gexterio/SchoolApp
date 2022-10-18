package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.PersonalCoursesDao;
import ua.com.foxminded.sql_jdbc_school.dto.CourseDTO;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

public class RemoveStudentFromCourse implements UserOption {
    PersonalCoursesDao personalCoursesDao;

    public RemoveStudentFromCourse(PersonalCoursesDao personalCoursesDao) {
        this.personalCoursesDao = personalCoursesDao;
    }

    public void removeStudentFromCourse (StudentDTO student, CourseDTO course) {
        personalCoursesDao.deleteStudentFromCourse(student, course);
    }
}
