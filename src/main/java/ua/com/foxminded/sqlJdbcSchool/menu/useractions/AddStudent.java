package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.util.Scanner;

@Component
public class AddStudent implements UserOption {
    JDBCTemplateStudentDao studentDao;

    @Autowired
    public AddStudent(JDBCTemplateStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter First Name");
        String firstName;
        String lastName;
        try {
            firstName = scanner.nextLine();
        } catch (Exception e) {
            throw new IllegalArgumentException("issue with Entering First Name");
        }
        System.out.println("Enter Last Name");
        try {
            lastName = scanner.nextLine();
        } catch (Exception e) {
            throw new IllegalArgumentException("issue with Entering Last Name");
        }
        addStudent(new StudentDTO.StudentBuilder(firstName, lastName).build());
    }

    private void addStudent(StudentDTO student) {
        studentDao.create(student);
    }
}
