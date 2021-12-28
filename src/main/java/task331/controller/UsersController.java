package task331.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/user")
    public String showUser(Authentication authentication, ModelMap model) {
        UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }

}
