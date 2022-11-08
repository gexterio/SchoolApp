package ua.com.foxminded.sql_jdbc_school.menu.useractions;

import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dto.StudentDTO;

import java.util.Scanner;

public class DeleteStudent implements UserOption {
    StudentDao studentDao;
    Scanner scanner;

    public DeleteStudent(StudentDao studentDao, Scanner scanner) {
        this.studentDao = studentDao;
        this.scanner =scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter student id");
        int id = Integer.parseInt(scanner.nextLine());
        deleteStudent(studentDao.searchById(id));
    }

    private void deleteStudent(StudentDTO student) {
        studentDao.delete(student);
    }
}
