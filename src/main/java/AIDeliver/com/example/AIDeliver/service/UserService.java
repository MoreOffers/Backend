package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.dto.UserDTO;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getUsers();
    Boolean addNewUser(UserDTO userDTO);
    void deleteUser(Long studentId);
    void save(User user);
    User findUserByEmail(String email);

//    List<Orders> getHistorySalesOrders(Long userId);
}
