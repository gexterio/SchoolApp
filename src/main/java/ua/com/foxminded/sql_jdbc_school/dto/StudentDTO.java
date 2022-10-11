package ua.com.foxminded.sql_jdbc_school.dto;

public class StudentDTO {
    private final Integer studentID;
    private final String studentFirstName;
    private final String studentLastName;
    private final String studentGroupId;

    public StudentDTO(Integer studentID, String studentFirstName,
                      String studentLastName, String studentGroup) {
        this.studentID = studentID;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentGroupId = studentGroup;
    }
}
