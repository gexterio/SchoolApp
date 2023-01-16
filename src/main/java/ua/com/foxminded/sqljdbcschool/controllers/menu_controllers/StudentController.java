package ua.com.foxminded.sqljdbcschool.controllers.menu_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students/")
public class StudentController {
    private static final String REDIRECT_MENU_SUCCESS = "redirect:success";
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    @Autowired
    public StudentController(@Qualifier("hibernateStudentDao") StudentDao studentDao, @Qualifier("hibernateCourseDao") CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @GetMapping("success")
    public String successPage() {
        return "menu/success";
    }

    @GetMapping("new")
    public String newStudent(@ModelAttribute("student") StudentDTO student) {
        return "students/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("student") StudentDTO student) {
        studentDao.create(student);
        return String.format("redirect:%d", student.getStudentId());
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("students", studentDao.getAll());
        return "students/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("student", studentDao.searchById(id));
        return "students/show";
    }

    @GetMapping("search")
    public String studentInfoPage() {
        return "students/search";
    }

    @PostMapping("toShow")
    public String toShow(@RequestParam("studentId") int id) {
        return String.format("redirect:%d", id);
    }


    @GetMapping("addStudentToCourseForm")
    public String addStudentToCoursePage() {
        return "students/addStudentToCourseForm";
    }

    @PostMapping("addStudentToCourseSuccess")
    public String addedToCoursePage(@RequestParam("studentId") Integer studentId,
                                    @RequestParam("courseId") Integer courseId) {
        StudentDTO student = studentDao.searchById(studentId);
        CourseDTO course = courseDao.searchById(courseId);
        studentDao.addStudentToCourse(student, course);
        return String.format("redirect:%d", studentId);
    }

    @GetMapping("delete")
    public String deleteStudentPage() {
        return "students/delete";
    }

    @PostMapping("deleteStudentSuccess")
    public String deletedStudentPage(@RequestParam("studentId") Integer studentId) {
        studentDao.delete(studentDao.searchById(studentId));
        return REDIRECT_MENU_SUCCESS;
    }

    @GetMapping("removeStudentFromCourseForm")
    public String removeStudentFromCoursePage() {
        return "students/removeStudentFromCourseForm";
    }

    @PostMapping("removeStudentFromCourseSuccess")
    public String removedStudentFromCoursePage(@RequestParam("studentId") Integer studentId,
                                               @RequestParam("courseId") Integer courseId) {
        StudentDTO student = studentDao.searchById(studentId);
        CourseDTO course = courseDao.searchById(courseId);
        studentDao.deleteStudentFromCourse(student, course);
        return String.format("redirect:%d", studentId);
    }

    @GetMapping("searchGroupsForm")
    public String searchGroupsPage() {
        return "students/searchGroupsForm";
    }

    @GetMapping("groupsCount")
    public String groupsCountPage(@RequestParam("count") int count,
                                  Model model) {
        model.addAttribute("msg", studentDao.searchGroupsByStudentCount(count));
        return "students/groupsCount";
    }

    @GetMapping("searchStudentsInCourseForm")
    public String searchStudentsInCoursePage() {
        return "students/searchStudentsInCourseForm";
    }

    @GetMapping("studentsInCourse")
    public String studentsInCoursePage(@RequestParam("courseName") String courseName, Model model) {
        List<Integer> studentsInCourse = courseDao.searchStudentsInCourse(courseName);
        List<StudentDTO> students = new ArrayList<>();
        studentsInCourse.forEach(course -> students.add(studentDao.searchById(course)));
        model.addAttribute("studentsInCourse", students);
        return "students/studentsInCourse";
    }


}
