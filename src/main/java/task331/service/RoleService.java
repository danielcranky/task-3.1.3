package task331.service;

import task331.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoles();
    Role getRole(String s);
    void addRole(Role role);
}
