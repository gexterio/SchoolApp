package ua.com.foxminded.sql_jdbc_school.dto;

import ua.com.foxminded.sql_jdbc_school.util.DTOInputValidator;

import java.util.Objects;

public class CourseDTO {
    private final Integer courseId;
    private final String courseName;
    private final String courseDescription;
    private static final DTOInputValidator validator = new DTOInputValidator();

    public CourseDTO(CourseBuilder builder) {
        this.courseDescription = builder.courseDescription;
        this.courseId = builder.courseId;
        this.courseName = builder.courseName;
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

    @Override
    public String toString() {
        return "CourseDTO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return courseId.equals(courseDTO.courseId) && courseName.equals(courseDTO.courseName) && courseDescription.equals(courseDTO.courseDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseDescription);
    }

    public static class CourseBuilder {

        private final String courseName;
        private Integer courseId;
        private String courseDescription;

        public CourseBuilder(String courseName) {
            this.courseName = courseName;
        }

        public CourseBuilder setCourseId(Integer id) {
            this.courseId = id;
            return this;
        }

        public CourseBuilder setDescription(String description) {
            this.courseDescription = description;
            return this;
        }

        public CourseDTO build() {
            CourseDTO course = new CourseDTO(this);
            validator.validateCourse(course);
            return course;
        }
    }
}
