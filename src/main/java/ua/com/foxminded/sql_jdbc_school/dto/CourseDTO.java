package ua.com.foxminded.sql_jdbc_school.dto;

public class CourseDTO {
final private Integer courseId;
final private String courseName;
final private String courseDecsriprion;

    public CourseDTO(Integer courseId, String courseName, String courseDecsriprion) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDecsriprion = courseDecsriprion;
    }
}
