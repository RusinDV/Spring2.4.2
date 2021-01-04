package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AuthGroupDao;
import web.model.AuthGroup;

import java.util.List;

@Service
public class AuthGroupServiceImpl implements AuthGroupService {
    @Autowired
    AuthGroupDao authGroupDao;

    @Override
    @Transactional
    public void createAuthGroup(AuthGroup authGroup) {
        authGroupDao.createAuthGroup(authGroup);
    }

    @Override
    @Transactional
    public AuthGroup readAuthGroup(Long idAuthGroup) {
        return authGroupDao.readAuthGroup(idAuthGroup);
    }

    @Override
    @Transactional
    public void updateAuthGroup(Long idAuthGroup, String name, String authGroup) {
        authGroupDao.updateAuthGroup(idAuthGroup, name, authGroup);
    }

    @Override
    @Transactional
    public void deleteAuthGroup(Long idAuthGroup) {
        authGroupDao.deleteAuthGroup(idAuthGroup);
    }

    @Override
    @Transactional
    public void deleteAuthGroupByName(String name) {
        authGroupDao.deleteAuthGroupByName(name);
    }

    @Override
    @Transactional
    public List<AuthGroup> getListAuthGroupByName(String name) {
        return authGroupDao.getListAuthGroupByName(name);
    }

    @Override
    @Transactional
    public List<AuthGroup> getAuthGroup() {
        return authGroupDao.getAuthGroup();
    }
}
