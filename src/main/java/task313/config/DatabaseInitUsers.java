package task313.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import task313.model.Role;
import task313.model.User;
import task313.service.RoleService;
import task313.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DatabaseInitUsers {

    UserService userService;
    RoleService roleService;

    public DatabaseInitUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    @Transactional
    public void initDatabase() {
        if (userService.listUsers().isEmpty()) {

            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");
            roleService.addRole(adminRole);
            roleService.addRole(userRole);

            User admin = new User("ivanov@mail.ru", "admin", "Ivan", "Ivanov",
                    new HashSet<>(Set.of(adminRole)));
            User user = new User("petrov@mail.ru", "user", "Petr", "Petrov",
                    new HashSet<>(Set.of(userRole)));
            userService.addUser(admin);
            userService.addUser(user);
        }
    }
}
