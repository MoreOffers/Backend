package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.SecurityService;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(path = "user")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();

        session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("/login")
    public String login() {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping
    public List<User> getUser() {
        return userService.getUsers();
    }

    @PostMapping(path = "/register")
    public void addNewUser(@RequestBody User user) {
        userService.addNewUser(user);
    }

    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userid") Long userId,
            User user
    ) {
        userService.updateUser(user);
    }
}
