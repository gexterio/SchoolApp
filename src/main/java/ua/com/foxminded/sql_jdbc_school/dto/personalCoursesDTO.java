package ua.com.foxminded.sql_jdbc_school.dto;

public class personalCoursesDTO {
    private final Integer id;
    private final Integer studentId;
    private final Integer courseId;

    public personalCoursesDTO(Integer id, Integer studentId, Integer courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
