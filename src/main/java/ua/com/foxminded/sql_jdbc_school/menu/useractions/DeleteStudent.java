package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

public class DeleteStudent implements UserOption {
    StudentDao studentDao;

    public DeleteStudent(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void deleteStudent (StudentDTO student) {
        studentDao.delete(student);
    }
}
