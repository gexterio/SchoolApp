package ua.com.foxminded.sql_jdbc_school.dto;

import ua.com.foxminded.sql_jdbc_school.util.DTOInputValidator;

import java.util.Objects;

public class StudentDTO {
    private final Integer studentId;
    private final String firstName;
    private final String lastName;
    private final Integer groupId;
    private static final DTOInputValidator validator = new DTOInputValidator();

    public StudentDTO(StudentBuilder builder) {
        this.studentId = builder.studentId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.groupId = builder.groupId;
    }

    public Integer getStudentId() {
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
        return "\nstudent_id = " + studentId +
                "\nfirst_name = " + firstName +
                "\nlast_name = " + lastName +
                "\ngroup_id = " + groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, groupId);
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
            validator.validateStudent(student);
            return student;
        }


    }

}
