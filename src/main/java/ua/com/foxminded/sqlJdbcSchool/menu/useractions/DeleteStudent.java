package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.util.Scanner;

@Component
public class DeleteStudent implements UserOption {
    JDBCTemplateStudentDao studentDao;

    @Autowired
    public DeleteStudent(JDBCTemplateStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
            if (id <= 0) {
                throw new IllegalArgumentException("id can't be less or equals than 0");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Issue with Entering id");
        }
        deleteStudent(studentDao.searchById(id));
    }

    private void deleteStudent(StudentDTO student) {
        studentDao.delete(student);
    }
}
