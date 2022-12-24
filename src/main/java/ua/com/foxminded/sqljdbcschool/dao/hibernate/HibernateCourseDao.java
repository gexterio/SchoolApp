package ua.com.foxminded.sqljdbcschool.dao.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dto.CourseDTO;

import java.util.List;

@Repository
public class HibernateCourseDao implements CourseDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateCourseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(CourseDTO course) {
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
        return session.get(CourseDTO.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO searchByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from CourseDTO c where courseName = ?", CourseDTO.class)
                .setParameter(0, name)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> searchStudentsInCourse(String courseName) {
        Session session = sessionFactory.getCurrentSession();
        return (List<Integer>) session.createQuery("select c from CourseDTO c where courseName = ?")
                .setParameter(0, courseName).getResultList();
    }

    @Override
    @Transactional
    public void batchCreate(List<CourseDTO> courses) {
        Session session = sessionFactory.getCurrentSession();
        for (CourseDTO course : courses) {
            session.persist(course);
        }
    }
}
