package task313.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import task313.model.Role;
import task313.model.User;
import task313.service.RoleService;
import task313.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(ModelMap model) {
        List<User> users = userService.listUsers();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authorizedUser", user);
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @GetMapping("/new")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @RequestParam List<String> listRoles) {

        Set<Role> userRoles = new HashSet<>();
        for (String role : listRoles) {
            userRoles.add(roleService.getRole(role));
        }
        user.setRoles(userRoles);

        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam List<String> listRoles) {

        Set<Role> userRoles = new HashSet<>();
        for (String role : listRoles) {
            userRoles.add(roleService.getRole(role));
        }
        user.setRoles(userRoles);

        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@ModelAttribute("user") User user) {
        userService.removeUser(user.getId());
        return "redirect:/admin";
    }
}
