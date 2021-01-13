package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.dao.UserDaoImplHibernate;
import web.model.AuthGroup;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public void createUser(User user) {
        userDao.createUser(user);
    }


    @Override
    @Transactional
    public User readUser(Long idUser) {
        return userDao.readUser(idUser);
    }


    @Override
    @Transactional
    public User redUserByName(String name) {
        return userDao.redUserByName(name);
    }


    @Override
    @Transactional
    public User redUserByNameAndLastName(String name, String lastName) {
        return userDao.redUserByNameAndLastName(name,lastName);
    }


    @Override
    @Transactional
    public void updateUser(Long idUser, String name, String lastname, int age, String password, List<AuthGroup> authGroupList) {
        userDao.updateUser(idUser, name, lastname, age, password,authGroupList);
    }


    @Override
    @Transactional
    public void deleteUser(Long idUser) {
        userDao.deleteUser(idUser);
    }


    @Override
    @Transactional
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
