package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getUsers();
    void addNewUser(@RequestBody User user);
    void deleteUser(Long studentId);
    void updateUser(@RequestBody User user);
    Optional<User> findUserByEmail(String email);
}
