package ua.com.foxminded.sqlJdbcSchool.controllers.menuControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateCourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

@Controller
@RequestMapping("/menu")
public class AddStudentToCourseController {
    private final JDBCTemplateStudentDao studentDao;
    private final JDBCTemplateCourseDao courseDao;
@Autowired
    public AddStudentToCourseController(JDBCTemplateStudentDao studentDao, JDBCTemplateCourseDao courseDao) {
        this.studentDao = studentDao;
    this.courseDao = courseDao;
}

    @GetMapping("/addStudentToCourse")
    public String addStudentToCoursePage() {
        return "menu/addStudentToCourse";
    }

    @GetMapping("/addedToCourseSuccess")
    public String addedToCoursePage(@RequestParam("studentId") Integer studentId,
                                    @RequestParam("courseId") Integer courseId) {
        StudentDTO student = studentDao.searchById(studentId);
        CourseDTO course = courseDao.searchById(courseId);
        studentDao.addStudentToCourse(student, course);
        return "menu/addedToCourseSuccess";
    }
}
