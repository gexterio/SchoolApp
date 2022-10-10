package ua.com.foxminded.sql_jdbc_school.dto;

public class StudentDTO {
    private final Integer studentID;
    private final String studentFirstName;
    private final String studentLastName;
    private final String studentGroup;
    private final String studentCourses;

    public StudentDTO(Integer studentID, String studentFirstName,
                      String studentLastName, String studentGroup, String studentCourses) {
        this.studentID = studentID;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentGroup = studentGroup;
        this.studentCourses = studentCourses;
    }
}
