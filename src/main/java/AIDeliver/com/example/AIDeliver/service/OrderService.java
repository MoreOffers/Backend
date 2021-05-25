package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.dto.request.OrderConfirmationRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
//    List<Orders> getHistorySalesOrders(Long userId);
//    Orders createOrder(OrderInfoRequest orderInfo, Selected option, User user);
//    Optional<Orders> salesOrderDetail(Long salesOrderId);
//    String getSalesOrderStatus(Long salesOrderId);
//    void updateSalesOrderStatus(Long salesOrderId, String status);

    String createOrder(OrderConfirmationRequest orderConfirmationRequest);
//    void setSalesOrderStatus(String status);
}