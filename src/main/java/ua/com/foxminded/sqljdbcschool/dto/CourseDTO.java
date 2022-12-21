package ua.com.foxminded.sqljdbcschool.dto;

import jakarta.persistence.*;
import ua.com.foxminded.sqljdbcschool.util.DTOInputValidator;

import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "courses")
public class CourseDTO {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_description")
    private String courseDescription;

    @ManyToMany(mappedBy = "courses")
    private List<StudentDTO> students;
    private static final DTOInputValidator validator = new DTOInputValidator();

    public CourseDTO() {
    }

    public CourseDTO(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

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

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
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
