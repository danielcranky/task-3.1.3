package task313.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import task313.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   PasswordEncoder bcryptEncoder;

   @PersistenceContext
   EntityManager em;

   @Autowired
   public UserDaoImp(@Lazy PasswordEncoder bcryptEncoder) {
      this.bcryptEncoder = bcryptEncoder;
   }

   @Override
   public void addUser(User user) {
      user.setPassword(bcryptEncoder.encode(user.getPassword()));
      em.persist(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      return em.createQuery("select u From User u", User.class).getResultList();
   }

   @Override
   public User getUser(Long id) {
      TypedQuery<User> q = em.createQuery("select u From User u where u.id = :id", User.class);
      q.setParameter("id", id);
      return q.getSingleResult();
   }

   @Override
   public void removeUser(Long id) {
      em.remove(getUser(id));
   }

   @Override
   public void updateUser(Long id, User user) {
      user.setId(id);
      if (!user.getPassword().equals("")) {
         user.setPassword(bcryptEncoder.encode(user.getPassword()));
      } else {
         user.setPassword(getUser(id).getPassword());
      }
      em.merge(user);
   }

   @Override
   public UserDetails getUserByName(String s) {
      TypedQuery<User> q = em.createQuery("select u From User u where u.email = :email", User.class);
      q.setParameter("email", s);
      return q.getSingleResult();
   }
}
