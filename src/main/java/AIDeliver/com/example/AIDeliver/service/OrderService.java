package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.enity.Order;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.Impl.DeliveryOption;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<Order> getHistorySalesOrders(Long userId);
    Boolean createSalesOrder(@RequestBody Order order, DeliveryOption deliveryOption, User user);
    Optional<Order> salesOrderDetail(Long salesOrderId);
    String getSalesOrderStatus(Long salesOrderId);
    void updateSalesOrderStatus(Long salesOrderId, String status);
    List<DeliveryOption> calculatePrice(@RequestBody Order order);


//    void setSalesOrderStatus(String status);
}