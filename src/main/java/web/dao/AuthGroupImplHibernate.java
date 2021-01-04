package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.AuthGroup;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AuthGroupImplHibernate implements AuthGroupDao {
    @Autowired
    EntityManagerFactory emf;

    @Override
    public void createAuthGroup(AuthGroup authGroup) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(authGroup);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public AuthGroup readAuthGroup(Long idAuthGroup) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        AuthGroup authGroup = em.find(AuthGroup.class, idAuthGroup);
        em.getTransaction().commit();
        em.close();
        return authGroup;
    }

    @Override
    public void updateAuthGroup(Long idAuthGroup, String name, String authGroup) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        AuthGroup auth = em.find(AuthGroup.class, idAuthGroup);
        auth.setName(name);
        auth.setAuthgroup(authGroup);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteAuthGroup(Long idAuthGroup) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(AuthGroup.class, idAuthGroup));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteAuthGroupByName(String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query from_authGroup = em.createQuery("select a from AuthGroup a where a.name=:name");
        from_authGroup.setParameter("name",name);
        List<AuthGroup> resultList = from_authGroup.getResultList();
        for (AuthGroup authGroup : resultList) {
            em.remove(authGroup);
        }
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public List<AuthGroup> getListAuthGroupByName(String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query from_authGroup = em.createQuery("select a from AuthGroup a where a.name=:name");
        from_authGroup.setParameter("name",name);
        List<AuthGroup> resultList = from_authGroup.getResultList();
        em.getTransaction().commit();
        em.close();
        return resultList;
    }

    @Override
    public List<AuthGroup> getAuthGroup() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query from_authGroup = em.createQuery("select a from AuthGroup a");
        List<AuthGroup> resultList = from_authGroup.getResultList();
        em.getTransaction().commit();
        em.close();
        return resultList;
    }
}
