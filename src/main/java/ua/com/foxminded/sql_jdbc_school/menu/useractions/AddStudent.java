package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.util.Scanner;

public class AddStudent implements UserOption {
    StudentDao studentDao;


    public AddStudent(StudentDao studentDao) {
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
            e.printStackTrace();
            throw new IllegalArgumentException("issue with Entering First Name");
        }
        System.out.println("Enter Last Name");
        try {
            lastName = scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issue with Entering Last Name");
        }
        addStudent(new StudentDTO.StudentBuilder(firstName, lastName).build());
    }

    private void addStudent(StudentDTO student) {
        studentDao.create(student);
    }
}
