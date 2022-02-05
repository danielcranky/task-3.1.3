package task313.service;

import task313.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void removeUser(Long id);
    void updateUser(Long id, User user);
    List<User> listUsers();
    User getUser(Long id);
}
