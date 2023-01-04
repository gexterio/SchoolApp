package ua.com.foxminded.sqljdbcschool.dto;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String courseName;
    @Column(name = "course_description")
    private String courseDescription;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses", cascade = CascadeType.REFRESH)
    private List<StudentDTO> students;

    public CourseDTO() {
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
        return courseName.equals(courseDTO.courseName) && courseDescription.equals(courseDTO.courseDescription);
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
            return new CourseDTO(this);
        }
    }
}
