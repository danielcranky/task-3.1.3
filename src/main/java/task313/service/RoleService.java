package task313.service;

import task313.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoles();
    Role getRole(String s);
    void addRole(Role role);
}
