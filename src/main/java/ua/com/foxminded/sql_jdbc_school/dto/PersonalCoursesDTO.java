package ua.com.foxminded.sql_jdbc_school.dto;

public class PersonalCoursesDTO {
    private  Integer studentId;
    private  Integer courseId;

    public PersonalCoursesDTO(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "studentID = " + studentId + " | "
                + "courseID = " + courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }
}
