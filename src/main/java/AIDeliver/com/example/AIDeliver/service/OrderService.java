package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.dto.*;
import AIDeliver.com.example.AIDeliver.dto.request.OrderConfirmationRequest;
import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
//    Optional<Orders> salesOrderDetail(Long salesOrderId);
//    String getSalesOrderStatus(Long salesOrderId);
//    void updateSalesOrderStatus(Long salesOrderId, String status);

    String createOrder(OrderDTO orderDTO, UserDTO userDTO, SelectedDTO selectedDTO);
    List<Orders> getHistorySalesOrders(Long userId);
    OrderHistoryDTO getHistorySalesOrdersByEmail(String email);
}