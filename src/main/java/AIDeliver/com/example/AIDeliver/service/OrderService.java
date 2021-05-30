package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.dto.OrderDTO;
import AIDeliver.com.example.AIDeliver.dto.PlaceOrderDTO;
import AIDeliver.com.example.AIDeliver.dto.SelectedDTO;
import AIDeliver.com.example.AIDeliver.dto.UserDTO;
import AIDeliver.com.example.AIDeliver.dto.request.OrderConfirmationRequest;
import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
//    Orders createOrder(OrderInfoRequest orderInfo, Selected option, User user);
//    Optional<Orders> salesOrderDetail(Long salesOrderId);
//    String getSalesOrderStatus(Long salesOrderId);
//    void updateSalesOrderStatus(Long salesOrderId, String status);

    String createOrder(OrderDTO orderDTO, UserDTO userDTO, SelectedDTO selectedDTO);
    List<Orders> getHistorySalesOrders(Long userId);

//    void setSalesOrderStatus(String status);
}