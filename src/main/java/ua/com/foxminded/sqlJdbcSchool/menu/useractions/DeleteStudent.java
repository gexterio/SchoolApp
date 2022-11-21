package ua.com.foxminded.sqlJdbcSchool.menu.useractions;

import ua.com.foxminded.sqlJdbcSchool.dao.StudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.util.Scanner;

public class DeleteStudent implements UserOption {
    StudentDao studentDao;

    public DeleteStudent(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
            if (id <=0) {
                throw new IllegalArgumentException("id can't be less or equals than 0");
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Issue with Entering id");
        }
        deleteStudent(studentDao.searchById(id));
    }

    private void deleteStudent(StudentDTO student) {
        studentDao.delete(student);
    }
}
