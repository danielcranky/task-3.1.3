package task331.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import task331.model.Role;
import task331.model.User;
import task331.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DatabaseInitUsers {

    UserService userService;

    @Autowired
    public DatabaseInitUsers(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    @Transactional
    void initDatabase() {
        if (userService.listUsers().isEmpty()) {
            User admin = new User("admin", "admin", "Ivan", "Ivanov", "ivanov@mail.ru",
                    new HashSet<>(Set.of(new Role("ROLE_ADMIN"))));
            User user = new User("user", "user", "Petr", "Petrov", "petrov@mail.ru",
                    new HashSet<>(Set.of(new Role("ROLE_USER"))));
            userService.addUser(admin);
            userService.addUser(user);
        }
    }
}
