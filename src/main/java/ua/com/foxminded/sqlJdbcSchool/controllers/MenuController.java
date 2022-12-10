package ua.com.foxminded.sqlJdbcSchool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateCourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.CourseDTO;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {
    private final JDBCTemplateStudentDao studentDao;



    @Autowired
    public MenuController(JDBCTemplateStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping("/")
    public String indexPage () {
        return "menu/menu";
    }
    @GetMapping("/menu")
    public String menuPage() {
        return "menu/menu";
    }

    @GetMapping("/searchStudent")
    public String studentInfoPage() {
        return "menu/searchStudent";
    }

    @GetMapping("/studentInfo")
    public String studentInfoPage(@RequestParam(value = "studentId") Integer studentId, Model model) {
        StudentDTO student = studentDao.searchById(studentId);
        model.addAttribute("student", student);
        return "menu/studentInfo";
    }
    @GetMapping("/students")
    public String studentsPage(Model model) {
        model.addAttribute("students", studentDao.getAll());
        return "menu/students";
    }




}
