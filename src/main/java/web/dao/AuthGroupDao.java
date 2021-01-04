package web.dao;

import web.model.AuthGroup;
import web.model.User;

import java.util.List;

public interface AuthGroupDao {
    void createAuthGroup(AuthGroup authGroup);
    AuthGroup readAuthGroup(Long idAuthGroup);
    void updateAuthGroup(Long idAuthGroup,String name, String authGroup);
    void deleteAuthGroup(Long idAuthGroup);
    void deleteAuthGroupByName(String name);
    List<AuthGroup> getListAuthGroupByName(String name);
    List<AuthGroup> getAuthGroup();
}
