package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.dao.UserDaoImplHibernate;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Transactional
    @Override
    public void createUser(User user) {
        userDao.createUser(user);
    }

    @Transactional
    @Override
    public User readUser(Long idUser) {
        return userDao.readUser(idUser);
    }

    @Transactional
    @Override
    public User redUserByName(String name) {
        return userDao.redUserByName(name);
    }

    @Transactional
    @Override
    public User redUserByNameAndLastName(String name, String lastName) {
        return userDao.redUserByNameAndLastName(name,lastName);
    }

    @Transactional
    @Override
    public void updateUser(Long idUser, String name, String lastname, int age, String password) {
        userDao.updateUser(idUser, name, lastname, age, password);
    }

    @Transactional
    @Override
    public void deleteUser(Long idUser) {
        userDao.deleteUser(idUser);
    }

    @Transactional
    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
