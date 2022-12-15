package ua.com.foxminded.sqljdbcschool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping()
    public String indexPage () {
        return "menu/menu";
    }
    @GetMapping("menu")
    public String menuPage() {
        return "menu/menu";
    }

    @GetMapping("menu/success")
    public String successPage() {
        return "menu/success";
    }
}
