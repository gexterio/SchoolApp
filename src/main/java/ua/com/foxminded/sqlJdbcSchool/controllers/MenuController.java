package ua.com.foxminded.sqlJdbcSchool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final JDBCTemplateStudentDao groupDao;

    private final JDBCTemplateCourseDao courseDao;

    @Autowired
    public MenuController(JDBCTemplateStudentDao studentDao, JDBCTemplateStudentDao groupDao, JDBCTemplateCourseDao courseDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
    }

    @GetMapping("/students")
    public String studentsPage(Model model) {
        model.addAttribute("students", studentDao.getAll());
        return "menu/students";
    }

    @GetMapping("/menu")
    public String menuPage() {
        return "menu/menu";
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
        return "redirect:menu";
    }

    @GetMapping("/deleteStudent")
    public String deleteStudentPage() {
        return "menu/deleteStudent";
    }

    @GetMapping("/deletedStudent")
    public String deletedStudentPage(@RequestParam("studentId") Integer studentId) {
        studentDao.delete(studentDao.searchById(studentId));
        return "redirect:menu";
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
