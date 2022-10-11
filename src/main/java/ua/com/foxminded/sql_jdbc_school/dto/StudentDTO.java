package ua.com.foxminded.sql_jdbc_school.dto;

public class StudentDTO {
    private final Integer studentID;
    private final String studentFirstName;
    private final String studentLastName;
    private final Integer studentGroupId;

    public StudentDTO(Integer studentID, String studentFirstName,
                      String studentLastName, Integer studentGroup) {
        this.studentID = studentID;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentGroupId = studentGroup;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public Integer getStudentGroupId() {
        return studentGroupId;
    }
}
