package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.common.Exception.ApiRequestException;
import AIDeliver.com.example.AIDeliver.dto.request.UserLoginRequest;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.SecurityService;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginRequest request) {

        ResponseEntity<User> response = null;

        User user = userService.findUserByEmail(request.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        try {
            if (user != null &&
                    encoder.matches(request.getPassword(), user.getPassword())) {
                response = new ResponseEntity<User>(user,HttpStatus.OK);
            }

        } catch (AuthenticationException e) {
            throw new ApiRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @GetMapping
    public List<User> getUser() {
        return userService.getUsers();
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {

        ResponseEntity<String> response = null;
        try {
            Boolean isSuccess = userService.addNewUser(user);
            response = new ResponseEntity(isSuccess.toString(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return response;
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
