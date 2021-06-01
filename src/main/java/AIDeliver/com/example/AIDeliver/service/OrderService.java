package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.dto.*;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderHistoryDTO getHistorySalesOrdersByEmail(String email);
    Orders getSalesOrderBytrackingNumber(String trackingNumber);
    String createOrder(OrderDTO orderDTO, UserDTO userDTO, SelectedDTO selectedDTO);
}