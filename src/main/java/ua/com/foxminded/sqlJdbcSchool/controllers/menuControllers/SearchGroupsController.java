package ua.com.foxminded.sqlJdbcSchool.controllers.menuControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqlJdbcSchool.dao.jdbc_template.JDBCTemplateStudentDao;

@Controller
public class SearchGroupsController {
    private final JDBCTemplateStudentDao studentDao;
@Autowired
    public SearchGroupsController(JDBCTemplateStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping("/searchGroups")
    public String searchGroupsPage() {
        return "menu/searchGroups";
    }

    @GetMapping("/groupsCount")
    public String groupsCountPage(@RequestParam(value = "count") int count,
                                  Model model) {
        model.addAttribute("msg", studentDao.searchGroupsByStudentCount(count));
        return "menu/groupsCount";
    }
}
