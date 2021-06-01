package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.dto.UserDTO;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    List<User> getUsers();

    Boolean addNewUser(UserDTO userDTO);

    Boolean updateUser(UserDTO userDTO);

    void deleteUser(Long studentId);

    User findUserByEmail(String email);

}
