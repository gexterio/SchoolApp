package ua.com.foxminded.sqljdbcschool.dao.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class HibernateStudentDao implements StudentDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateStudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(StudentDTO student) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" select s from StudentDTO s", StudentDTO.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO searchById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(StudentDTO.class, id);
    }

    @Override
    @Transactional
    public void delete(StudentDTO student) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(student);
    }

    @Override
    @Transactional
    public void addStudentToGroup(StudentDTO student, Integer groupId) {
        Session session = sessionFactory.getCurrentSession();
        StudentDTO updatedStudent = session.get(StudentDTO.class, student.getStudentId());
        updatedStudent.setGroupId(groupId);
    }

    @Override
    @Transactional
    public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        Session session = sessionFactory.getCurrentSession();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        courseDTOS.add(course);
        StudentDTO updatedStudent = session.get(StudentDTO.class, student.getStudentId());
        updatedStudent.setCourses(courseDTOS);
    }

    @Override
    @Transactional
    public void deleteStudentFromCourse(StudentDTO student, CourseDTO course) {
        Session session = sessionFactory.getCurrentSession();
        StudentDTO updatedStudent = session.get(StudentDTO.class, student.getStudentId());
        List<CourseDTO> courses = student.getCourses();
        CourseDTO reqCourse = courses.stream()
                .filter(c -> c.getCourseName().equalsIgnoreCase(course.getCourseName()))
                .findFirst().orElseThrow();
        courses.remove(reqCourse);
        updatedStudent.setCourses(courses);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Integer> searchGroupsByStudentCount(Integer studentCount) {
        List<StudentDTO> students = getAll();
        Map<Integer, Integer> countedGroups = students.stream().collect(Collectors.toMap(StudentDTO::getGroupId, student -> 1, Integer::sum));
        return countedGroups.entrySet().stream()
                .filter(e -> e.getKey() != null)
                .filter(e -> e.getValue() <= studentCount)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    @Transactional
    public void batchCreate(List<StudentDTO> students) {
        Session session = sessionFactory.getCurrentSession();
        for (StudentDTO student : students) {
            session.persist(student);
        }
    }

    @Override
    @Transactional
    public void batchAddStudentToGroup(List<StudentDTO> students) {
        Session session = sessionFactory.getCurrentSession();
        for (StudentDTO student : students) {
            StudentDTO updatedStudent = session.get(StudentDTO.class, student.getStudentId());
            updatedStudent.setGroupId(student.getGroupId());
        }
    }

    @Override
    @Transactional
    public void batchAddStudentToCourse(Map<StudentDTO, CourseDTO> studentCoursesMap) {
        Session session = sessionFactory.getCurrentSession();
        for (Map.Entry<StudentDTO, CourseDTO> studentDTOCourseDTOEntry : studentCoursesMap.entrySet()) {
            StudentDTO updatedStudent = session.get(StudentDTO.class, studentDTOCourseDTOEntry.getKey().getStudentId());
            updatedStudent.setCourses(List.of(studentDTOCourseDTOEntry.getValue()));
        }
    }
}
