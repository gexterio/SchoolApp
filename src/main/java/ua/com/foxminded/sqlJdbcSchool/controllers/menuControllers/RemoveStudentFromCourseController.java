package ua.com.foxminded.sqlJdbcSchool.controllers.menuControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateCourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

@Controller
public class RemoveStudentFromCourseController {
    private final JDBCTemplateStudentDao studentDao;
    private final JDBCTemplateCourseDao courseDao;
@Autowired
    public RemoveStudentFromCourseController(JDBCTemplateStudentDao studentDao, JDBCTemplateCourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @GetMapping("/removeStudentFromCourse")
    public String removeStudentFromCoursePage() {
        return "menu/removeStudentFromCourse";
    }

    @GetMapping("/removedStudentFromCourse")
    public String removedStudentFromCoursePage(@RequestParam("studentId") Integer studentId,
                                               @RequestParam("courseId") Integer courseId) {
        StudentDTO student = studentDao.searchById(studentId);
        CourseDTO course = courseDao.searchById(courseId);
        studentDao.deleteStudentFromCourse(student,course);
        return "redirect:menu";
    }
}
