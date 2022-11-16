package ua.com.foxminded.sql_jdbc_school.dto;

public class CourseDTO {
    private final Integer courseId;
    private final String courseName;
    private final String courseDescription;

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

    public static class CourseBuilder {
        private final String courseName;
        private Integer courseId;
        private String courseDescription;

        public CourseBuilder(String courseName) {
            this.courseName = courseName;
        }

        public CourseBuilder setId(Integer id) {
            this.courseId = id;
            return this;
        }

        public CourseBuilder setDescription(String description) {
            this.courseDescription = description;
            return this;
        }

        public CourseDTO build() {
            CourseDTO course = new CourseDTO(this);
            validateCourseDTO(course);
            return course;
        }

        private void validateCourseDTO(CourseDTO course) {
            if (course.getCourseName() == null) {
                throw new IllegalArgumentException("groupName can't be NULL");
            }
            if (course.getCourseName().isEmpty()) {
                throw new IllegalArgumentException("groupName can't be EMPTY");
            }
            if (course.getCourseName().isBlank()) {
                throw new IllegalArgumentException("groupName can't be BLANK");
            }
        }
    }
}
