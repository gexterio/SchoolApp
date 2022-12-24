package ua.com.foxminded.sqljdbcschool.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.dto.GroupDTO;

import java.util.List;

@Repository
public class HibernateGroupDao implements GroupDao {
    SessionFactory sessionFactory;

    @Autowired
    public HibernateGroupDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(GroupDTO group) {
        Session session = sessionFactory.getCurrentSession();
        session.save(group);
    }

    @Override
    @Transactional
    public void batchCreate(List<GroupDTO> groups) {
        Session session = sessionFactory.getCurrentSession();
        for (GroupDTO group : groups) {
            session.persist(group);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDTO> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return (List<GroupDTO>) session.createQuery("select  g from GroupDTO g ").getResultList();
    }

}
