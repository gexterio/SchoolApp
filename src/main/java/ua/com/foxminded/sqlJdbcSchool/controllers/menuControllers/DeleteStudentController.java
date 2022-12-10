package ua.com.foxminded.sqlJdbcSchool.controllers.menuControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;

@Controller
@RequestMapping("/menu")
public class DeleteStudentController {
    private final JDBCTemplateStudentDao studentDao;
@Autowired
    public DeleteStudentController(JDBCTemplateStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping("/deleteStudent")
    public String deleteStudentPage() {
        return "menu/deleteStudent";
    }

    @GetMapping("/deletedStudent")
    public String deletedStudentPage(@RequestParam("studentId") Integer studentId) {
        studentDao.delete(studentDao.searchById(studentId));
        return "menu/deletedStudent";
    }
}
