package web.dao;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;
import web.model.AuthGroup;
import web.model.User;


import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImplHibernate implements UserDao {

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public void createUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.flush();
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public User readUser(Long idUser) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, idUser);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return user;
    }

    @Override
    public User redUserByName(String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select u from User u where u.name=:name");
        query.setParameter("name", name);
        User user = (User) query.getSingleResult();
        em.flush();
        em.getTransaction().commit();
        em.close();
        return user;
    }

    @Override
    public User redUserByNameAndLastName(String name, String lastName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select u from User u where u.name=:name and u.lastName=:lastName");
        query.setParameter("name", name);
        query.setParameter("lastName", lastName);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        em.flush();
        em.getTransaction().commit();
        em.close();
        return user;
    }

    @Override
    public void updateUser(Long idUser, String name, String lastname, int age, String password, List<AuthGroup> authGroupList) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, idUser);
        user.setName(name);
        user.setLastName(lastname);
        user.setAge(age);
        user.setPassword(password);
        user.setAuthGroupList(authGroupList);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteUser(Long idUser) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class, idUser));
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<User> getUsers() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query from_user = em.createQuery("select u from User u");
        List<User> resultList = from_user.getResultList();
        em.flush();
        em.getTransaction().commit();
        em.close();
        return resultList;
    }
}
