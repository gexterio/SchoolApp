package ua.com.foxminded.sqljdbcschool.controllers.menu_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateCourseDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateStudentDao;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menu/")
public class StudentController {
    private static final String REDIRECT_MENU_SUCCESS = "redirect:success";
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    @Autowired
    public StudentController(@Qualifier("hibernateStudentDao") StudentDao studentDao, HibernateCourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @GetMapping("addStudentForm")
    public String addStudentPage() {
        return "menu/addStudentForm";
    }

    @PostMapping("addStudentSuccess")
    public String addedSuccessPage(@RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName) {
        studentDao.create(new StudentDTO.StudentBuilder(firstName, lastName).build());
        return REDIRECT_MENU_SUCCESS;
    }

    @GetMapping("addStudentToCourseForm")
    public String addStudentToCoursePage() {
        return "menu/addStudentToCourseForm";
    }

    @PostMapping("addStudentToCourseSuccess")
    public String addedToCoursePage(@RequestParam("studentId") Integer studentId,
                                    @RequestParam("courseId") Integer courseId) {
        StudentDTO student = studentDao.searchById(studentId);
        CourseDTO course = courseDao.searchById(courseId);
        studentDao.addStudentToCourse(student, course);
        return REDIRECT_MENU_SUCCESS;
    }

    @GetMapping("deleteStudentForm")
    public String deleteStudentPage() {
        return "menu/deleteStudentForm";
    }

    @PostMapping("deleteStudentSuccess")
    public String deletedStudentPage(@RequestParam("studentId") Integer studentId) {
        studentDao.delete(studentDao.searchById(studentId));
        return REDIRECT_MENU_SUCCESS;
    }

    @GetMapping("removeStudentFromCourseForm")
    public String removeStudentFromCoursePage() {
        return "menu/removeStudentFromCourseForm";
    }

    @PostMapping("removeStudentFromCourseSuccess")
    public String removedStudentFromCoursePage(@RequestParam("studentId") Integer studentId,
                                               @RequestParam("courseId") Integer courseId) {
        StudentDTO student = studentDao.searchById(studentId);
        CourseDTO course = courseDao.searchById(courseId);
        studentDao.deleteStudentFromCourse(student, course);
        return REDIRECT_MENU_SUCCESS;
    }

    @GetMapping("searchGroupsForm")
    public String searchGroupsPage() {
        return "menu/searchGroupsForm";
    }

    @GetMapping("groupsCount")
    public String groupsCountPage(@RequestParam(value = "count") int count,
                                  Model model) {
        model.addAttribute("msg", studentDao.searchGroupsByStudentCount(count));
        return "menu/groupsCount";
    }

    @GetMapping("searchStudentsInCourseForm")
    public String searchStudentsInCoursePage() {
        return "menu/searchStudentsInCourseForm";
    }

    @GetMapping("studentsInCourse")
    public String studentsInCoursePage(@RequestParam("courseName") String courseName, Model model) {
        List<Integer> studentsInCourse = courseDao.searchStudentsInCourse(courseName);
        List<String> students = new ArrayList<>();
        studentsInCourse.forEach(course -> students.add(studentDao.searchById(course).getFirstName()));
        model.addAttribute("studentsInCourse", students);
        return "menu/studentsInCourse";
    }

    @GetMapping("searchStudentForm")
    public String studentInfoPage() {
        return "menu/searchStudentForm";
    }

    @GetMapping("studentInfo")
    public String studentInfoPage(@RequestParam(value = "studentId") Integer studentId, Model model) {
        StudentDTO student = studentDao.searchById(studentId);
        model.addAttribute("student", student);
        return "menu/studentInfo";
    }

    @GetMapping("students")
    public String studentsPage(Model model) {
        model.addAttribute("students", studentDao.getAll());
        return "menu/students";
    }
}
