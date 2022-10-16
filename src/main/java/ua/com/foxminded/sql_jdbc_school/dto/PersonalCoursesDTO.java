package ua.com.foxminded.sql_jdbc_school.dto;

public class PersonalCoursesDTO {
    private  Integer studentId;
    private  Integer courseId;

    public PersonalCoursesDTO(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
