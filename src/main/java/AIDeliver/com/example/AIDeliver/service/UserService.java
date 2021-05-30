package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getUsers();
    Boolean addNewUser(User user);
    void deleteUser(Long studentId);
    void save(User user);
    User findUserByEmail(String email);
    User findUserById(Long id);


//    List<Orders> getHistorySalesOrders(Long userId);
}
