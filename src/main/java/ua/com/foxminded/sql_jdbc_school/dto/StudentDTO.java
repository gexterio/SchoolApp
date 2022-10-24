package ua.com.foxminded.sql_jdbc_school.dto;

public class StudentDTO {
    private final String firstName;
    private final String lastName;
    private Integer studentId;
    private Integer groupId;

    public StudentDTO(StudentBuilder builder) {
        this.studentId = builder.studentId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.groupId = builder.groupId;
    }

    public Integer getStudentID() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentID=" + studentId +
                ", groupId=" + groupId +
                '}';
    }

    public static class StudentBuilder {
        private final String firstName;
        private final String lastName;
        private Integer studentId;
        private Integer groupId;

        public StudentBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public StudentBuilder setStudentId(Integer id) {
            this.studentId = id;
            return this;
        }

        public StudentBuilder setGroupId(Integer id) {
            this.groupId = id;
            return this;
        }

        public StudentDTO build() {
            StudentDTO student = new StudentDTO(this);
            validateStudentDTO(student);
            return student;
        }

        private void validateStudentDTO(StudentDTO student) {

        }
    }

}
