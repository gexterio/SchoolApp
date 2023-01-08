package ua.com.foxminded.sqljdbcschool.dto;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "students")
public class StudentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "group_id")
    private Integer groupId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "personal_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<CourseDTO> courses;

    public StudentDTO() {

    }

    public StudentDTO(StudentBuilder builder) {
        this.studentId = builder.studentId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.groupId = builder.groupId;
    }

    public void addCourse(CourseDTO course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        if (!courses.contains(course)) {
            courses.add(course);
        } else {
            throw new IllegalArgumentException("student already in course: " + course.getCourseName());
        }
    }

    public void removeCourse(CourseDTO course) {
        if (courses.contains(course)) {
            courses.remove(course);
        } else throw new IllegalArgumentException("course not found");
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "\nstudent_id = " + studentId +
                "\nfirst_name = " + firstName +
                "\nlast_name = " + lastName +
                "\ngroup_id = " + groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, groupId);
    }

    public static class StudentBuilder {
        private final String firstName;
        private final String lastName;
        private Integer studentId;
        private Integer groupId;

        public StudentBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public StudentBuilder setStudentId(Integer id) {
            this.studentId = id;
            return this;
        }

        public StudentBuilder setGroupId(Integer id) {
            this.groupId = id;
            return this;
        }

        public StudentDTO build() {
            return new StudentDTO(this);
        }


    }

}
