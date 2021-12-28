package task331.dao;

import org.springframework.security.core.userdetails.UserDetails;
import task331.model.User;

import java.util.List;

public interface UserDao {
   void addUser(User user);
   void removeUser(Long id);
   void updateUser(Long id, User user);
   List<User> listUsers();
   User getUser(Long id);
   UserDetails getUserByName(String s);
}
