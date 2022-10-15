package ua.com.foxminded.sql_jdbc_school.dto;

public class StudentDTO {
    private final Integer studentID;
    private final String studentFirstName;
    private final String studentLastName;

    private Integer studentGroupId;

    public StudentDTO(Integer studentID, String studentFirstName,
                      String studentLastName) {
        this.studentID = studentID;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
    }

    public void setStudentGroupId(Integer studentGroupId) {
        this.studentGroupId = studentGroupId;
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
