package task313.dao;

import task313.model.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> getRoles();
    Role getRole(String s);
    void addRole(Role role);
}
