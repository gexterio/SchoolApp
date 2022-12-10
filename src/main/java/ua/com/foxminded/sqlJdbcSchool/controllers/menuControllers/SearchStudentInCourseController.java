package ua.com.foxminded.sqlJdbcSchool.controllers.menuControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateCourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class SearchStudentInCourseController {

    private final JDBCTemplateStudentDao studentDao;
    private final JDBCTemplateCourseDao courseDao;

    @Autowired
    public SearchStudentInCourseController(JDBCTemplateStudentDao studentDao, JDBCTemplateCourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @GetMapping("/searchStudentsInCourse")
    public String searchStudentsInCoursePage() {
        return "menu/searchStudentsInCourse";
    }

    @GetMapping("/studentsInCourse")
    public String studentsInCoursePage(@RequestParam("courseName") String courseName, Model model) {
        List<Integer> studentsInCourse = courseDao.searchStudentsInCourse(courseName);
        List<String> students = new ArrayList<>();
        studentsInCourse.forEach(course -> students.add(studentDao.searchById(course).getFirstName()));
        model.addAttribute("studentsInCourse", students);
        return "menu/studentsInCourse";
    }
}
