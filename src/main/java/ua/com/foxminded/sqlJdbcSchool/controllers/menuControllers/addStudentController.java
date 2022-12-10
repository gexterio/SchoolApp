package ua.com.foxminded.sqlJdbcSchool.controllers.menuControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqlJdbcSchool.dao.CourseDao;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;
import ua.com.foxminded.sqlJdbcSchool.dto.StudentDTO;
@Controller
public class addStudentController {
    private final JDBCTemplateStudentDao studentDao;

    @Autowired
    public addStudentController(JDBCTemplateStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping("/addStudent")
    public String addStudentPage() {
        return "menu/addStudent";
    }

    @GetMapping("/addedSuccess")
    public String addedSuccessPage(@RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName) {
        studentDao.create(new StudentDTO.StudentBuilder(firstName, lastName).build());
        return "redirect:menu";
    }
}
