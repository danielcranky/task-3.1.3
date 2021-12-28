package task331.dao;

import org.springframework.stereotype.Repository;
import task331.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(em.createQuery("Select r From Role r", Role.class).getResultList());
    }

    @Override
    public Role getRole(String s) {
        TypedQuery<Role> q = em.createQuery("Select r From Role r where r.roleName = :role", Role.class);
        q.setParameter("role", s);
        return q.getSingleResult();
    }
}
