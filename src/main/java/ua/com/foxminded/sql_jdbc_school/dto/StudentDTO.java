package ua.com.foxminded.sql_jdbc_school.dto;

public class StudentDTO {
    private final Integer studentID;
    private final String firstName;
    private final String lastName;
    private Integer groupId;

    public StudentDTO(Integer studentID, String firstName,
                      String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StudentDTO(Integer studentID, String firstName, String lastName, Integer groupId) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "studentID = " + this.studentID.toString() + " | "
                + "firstName = " + this.firstName + " | "
                + "lastName = " + this.lastName + " | "
                + "groupID = " + this.groupId;
    }
}
