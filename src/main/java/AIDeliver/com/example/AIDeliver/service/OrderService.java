package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<Order> getHistoryOrders(Long userId);
    void createOrder(Order order, Deliverer deliverer);
    Optional<Order> orderDetail(Long orderId);
    String getOrderStatus(Long orderId);
    void updateOrderStatus(Long orderId, String status);

    //List<Order> findOrderByUserId(Long userId);
}