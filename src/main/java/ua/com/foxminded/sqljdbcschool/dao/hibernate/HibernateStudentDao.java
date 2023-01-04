package ua.com.foxminded.sqljdbcschool.dao.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;
import ua.com.foxminded.sqljdbcschool.util.DTOInputValidator;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class HibernateStudentDao implements StudentDao {

    private final SessionFactory sessionFactory;
    private final DTOInputValidator inputValidator;

    @Autowired
    public HibernateStudentDao(SessionFactory sessionFactory, DTOInputValidator inputValidator) {
        this.sessionFactory = sessionFactory;
        this.inputValidator = inputValidator;
    }

    @Override
    @Transactional
    public void create(StudentDTO student) {
        inputValidator.validateStudent(student);
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
        StudentDTO studentDTO = session.get(StudentDTO.class, id);
        inputValidator.validateStudent(studentDTO);
        return studentDTO;
    }

    @Override
    @Transactional
    public void delete(StudentDTO student) {
        inputValidator.validateStudent(student);
        Session session = sessionFactory.getCurrentSession();
        StudentDTO studentFromDB = session.get(StudentDTO.class, student.getStudentId());
        if (studentFromDB != null) {
            session.remove(studentFromDB);
        }
    }

    @Override
    @Transactional
    public void addStudentToGroup(StudentDTO student, Integer groupId) {
        inputValidator.validateStudent(student);
        Session session = sessionFactory.getCurrentSession();
        StudentDTO updatedStudent = session.get(StudentDTO.class, student.getStudentId());
        updatedStudent.setGroupId(groupId);
    }

    @Override
    @Transactional
    public void addStudentToCourse(StudentDTO student, CourseDTO course) {
        inputValidator.validateStudent(student);
        inputValidator.validateCourse(course);
        Session session = sessionFactory.getCurrentSession();
        StudentDTO studentDTO = session.get(StudentDTO.class, student.getStudentId());
        CourseDTO courseDTO = session.get(CourseDTO.class, course.getCourseId());
        studentDTO.addCourse(courseDTO);
    }

    @Override
    @Transactional()
    public void deleteStudentFromCourse(StudentDTO student, CourseDTO course) {
        inputValidator.validateStudent(student);
        inputValidator.validateCourse(course);
        Session session = sessionFactory.getCurrentSession();
        StudentDTO updatedStudent = session.get(StudentDTO.class, student.getStudentId());
        try {
            updatedStudent.removeCourse(course);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Integer> searchGroupsByStudentCount(Integer studentCount) {
        if (studentCount == null || studentCount < 0) {
            throw new IllegalArgumentException("Count of students can't be Null or less than zero");
        }
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
        students.forEach(inputValidator::validateStudent);
        Session session = sessionFactory.getCurrentSession();
        for (StudentDTO student : students) {
            session.persist(student);
        }
    }

    @Override
    @Transactional
    public void batchAddStudentToGroup(List<StudentDTO> students) {
        students.forEach(inputValidator::validateStudent);
        Session session = sessionFactory.getCurrentSession();
        for (StudentDTO student : students) {
            StudentDTO updatedStudent = session.get(StudentDTO.class, student.getStudentId());
            updatedStudent.setGroupId(student.getGroupId());
        }
    }

    @Override
    @Transactional
    public void batchAddStudentToCourse(Map<StudentDTO, CourseDTO> studentCoursesMap) {
        studentCoursesMap.forEach((key, value) -> {
            inputValidator.validateStudent(key);
            inputValidator.validateCourse(value);
        });
        Session session = sessionFactory.getCurrentSession();
        for (Map.Entry<StudentDTO, CourseDTO> studentDTOCourseDTOEntry : studentCoursesMap.entrySet()) {
            StudentDTO updatedStudent = session.get(StudentDTO.class, studentDTOCourseDTOEntry.getKey().getStudentId());
            updatedStudent.setCourses(List.of(studentDTOCourseDTOEntry.getValue()));
        }
    }
}
