package ua.com.foxminded.sqljdbcschool.dao.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;
import ua.com.foxminded.sqljdbcschool.dto.StudentDTO;
import ua.com.foxminded.sqljdbcschool.util.DTOInputValidator;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HibernateCourseDao implements CourseDao {
    private final SessionFactory sessionFactory;

    private final DTOInputValidator inputValidator;


    @Autowired
    public HibernateCourseDao(SessionFactory sessionFactory, DTOInputValidator inputValidator) {
        this.sessionFactory = sessionFactory;
        this.inputValidator = inputValidator;
    }

    @Override
    @Transactional
    public void create(CourseDTO course) {
        inputValidator.validateCourse(course);
        Session session = sessionFactory.getCurrentSession();
        session.persist(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from CourseDTO c", CourseDTO.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO searchById(int id) {
        Session session = sessionFactory.getCurrentSession();
        CourseDTO courseDTO = session.get(CourseDTO.class, id);
        inputValidator.validateCourse(courseDTO);
        return courseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO searchByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CourseDTO courseDTO = session.createQuery("select c from CourseDTO c where courseName = ?1", CourseDTO.class)
                    .setParameter(1, name)
                    .getSingleResult();
            inputValidator.validateCourse(courseDTO);
            return courseDTO;
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Can't find course with input name");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> searchStudentsInCourse(String courseName) {
        Session session = sessionFactory.getCurrentSession();
        CourseDTO course =(CourseDTO) session.createQuery("select c from CourseDTO c where courseName = ?1")
                .setParameter(1, courseName).getSingleResult();
        return course.getStudents().stream().map(StudentDTO::getStudentId).collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void batchCreate(List<CourseDTO> courses) {
        courses.forEach(inputValidator::validateCourse);
        Session session = sessionFactory.getCurrentSession();
        for (CourseDTO course : courses) {
            session.persist(course);
        }
    }
}
