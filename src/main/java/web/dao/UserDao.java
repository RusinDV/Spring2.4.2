package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    void createUser(User user);
    User readUser(Long idUser);
    User redUserByName(String name);
    User redUserByNameAndLastName(String name,String lastName);
    void updateUser(Long idUser,String name, String lastname, int age, String password);
    void deleteUser(Long idUser);
    List<User> getUsers();
}
