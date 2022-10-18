package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

public class AddStudent implements UserOption{
    StudentDao studentDao;

    public AddStudent(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
    public void addStudent(StudentDTO student) {
        studentDao.create(student);
    }
}
