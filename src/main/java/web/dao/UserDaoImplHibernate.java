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

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createUser(User user) {
        em.persist(user);
    }

    @Override
    public User readUser(Long idUser) {
        User user = em.find(User.class, idUser);
        return user;
    }

    @Override
    public User readUserByName(String name) {
        Query query = em.createQuery("select u from User u where u.name=:name");
        query.setParameter("name", name);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public User readUserByNameAndLastName(String name, String lastName) {
        Query query = em.createQuery("select u from User u where u.name=:name and u.lastName=:lastName");
        query.setParameter("name", name);
        query.setParameter("lastName", lastName);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateUser(Long idUser, String name, String lastname, int age, String password, List<AuthGroup> authGroupList) {
        User user = em.find(User.class, idUser);
        user.setName(name);
        user.setLastName(lastname);
        user.setAge(age);
        user.setPassword(password);
        user.setAuthGroupList(authGroupList);
    }

    @Override
    public void deleteUser(Long idUser) {
        em.remove(em.find(User.class, idUser));
    }

    @Override
    public List<User> getUsers() {
        Query from_user = em.createQuery("select u from User u");
        List<User> resultList = from_user.getResultList();
        return resultList;
    }
}
