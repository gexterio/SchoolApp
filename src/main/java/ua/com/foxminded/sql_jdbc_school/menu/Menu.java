package ua.com.foxminded.sql_jdbc_school.menu;

import ua.com.foxminded.sql_jdbc_school.dao.CourseDao;
import ua.com.foxminded.sql_jdbc_school.dao.StudentDao;
import ua.com.foxminded.sql_jdbc_school.dao.connection.BasicConnectionPool;
import ua.com.foxminded.sql_jdbc_school.menu.useractions.*;

public class Menu {
    BasicConnectionPool basicConnectionPool;
    StudentDao studentDao;
    CourseDao courseDao;
    public AddStudent addStudent;
    public AddStudentToCourse addStudentToCourse;
    public DeleteStudent deleteStudent;
    public Exit exit;
    public RemoveStudentFromCourse removeStudentFromCourse;
    public SearchGroups searchGroups;
    public SearchStudentsInCourse searchStudentsInCourse;

    public Menu(BasicConnectionPool basicConnectionPool, StudentDao studentDao, CourseDao courseDao
    ) {
        this.basicConnectionPool = basicConnectionPool;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.addStudent = new AddStudent(studentDao);
        this.addStudentToCourse = new AddStudentToCourse(studentDao);
        this.deleteStudent = new DeleteStudent(studentDao);
        this.exit = new Exit(basicConnectionPool);
        this.removeStudentFromCourse = new RemoveStudentFromCourse(studentDao);
        this.searchGroups = new SearchGroups(studentDao);
        this.searchStudentsInCourse = new SearchStudentsInCourse(courseDao);
    }
}
