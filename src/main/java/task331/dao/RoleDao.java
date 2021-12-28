package task331.dao;

import task331.model.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> getRoles();
    Role getRole(String s);
}
