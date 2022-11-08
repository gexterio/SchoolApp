package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.util.Scanner;

public class AddStudent implements UserOption{
    StudentDao studentDao;
    Scanner scanner;

    public AddStudent(StudentDao studentDao, Scanner scanner) {
        this.studentDao = studentDao;
       this.scanner = scanner;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter First Name");
        String firstName = scanner.nextLine();
        System.out.println("Enter Last Name");
        String lastName = scanner.nextLine();
        addStudent(new StudentDTO.StudentBuilder(firstName,lastName).build());
    }

    public void addStudent(StudentDTO student) {
        studentDao.create(student);
    }
}
