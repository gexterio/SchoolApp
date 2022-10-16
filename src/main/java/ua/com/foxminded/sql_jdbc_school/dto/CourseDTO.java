package ua.com.foxminded.sql_jdbc_school.dto;

public class CourseDTO {
    private final Integer courseId;
    private final String courseName;

    private final String courseDescription;

    public CourseDTO(Integer courseId, String courseName, String courseDecsriprion) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDecsriprion;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }
}
