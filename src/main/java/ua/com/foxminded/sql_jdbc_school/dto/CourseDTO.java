package ua.com.foxminded.sql_jdbc_school.dto;

public class CourseDTO {
final private Integer courseId;
final private String courseName;

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    final private String courseDescription;

    public CourseDTO(Integer courseId, String courseName, String courseDecsriprion) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDecsriprion;
    }
}
