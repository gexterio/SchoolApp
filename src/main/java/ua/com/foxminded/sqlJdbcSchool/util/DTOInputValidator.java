package ua.com.foxminded.sqlJdbcSchool.util;

import org.springframework.stereotype.Component;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.GroupDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

@Component
public class DTOInputValidator {

    public void validateStudent(StudentDTO student) {
        if (student == null) {
            throw new IllegalArgumentException("studentDTO can't be NULL.");
        }
        if (student.getFirstName() == null) {
            throw new IllegalArgumentException("firstName can't be NULL");
        }
        if (student.getFirstName().isBlank() || student.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("firstName can't be Blank or Empty");
        }
        if (student.getLastName() == null) {
            throw new IllegalArgumentException("firstName can't be NULL");
        }
        if (student.getLastName().isBlank() || student.getLastName().isEmpty()) {
            throw new IllegalArgumentException("lastName can't be BLANK or EMPTY");
        }
    }

    public void validateCourse(CourseDTO course) {
        if (course == null) {
            throw new IllegalArgumentException("course can't be NULL");
        }
        if (course.getCourseName() == null) {
            throw new IllegalArgumentException("courseName can't be NULL");
        }
        if (course.getCourseName().isEmpty() || course.getCourseName().isBlank()) {
            throw new IllegalArgumentException("courseName can't be BLANK orEMPTY");
        }
    }

    public void validateGroup(GroupDTO group) {
        if (group == null) {
            throw new IllegalArgumentException("group can't be NULL");
        }
        if (group.getGroupName() == null) {
            throw new IllegalArgumentException("groupName can't be NULL");
        }
        if (!group.getGroupName().matches("^\\d{2}+-+[A-Z]{2}")) {
            throw new IllegalArgumentException("invalidGroupName: " + group.getGroupName());
        }
    }
}
