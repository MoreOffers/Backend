package AIDeliver.com.example.AIDeliver.service;


import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.SalesOrder;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.Impl.DeliveryOption;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public interface SalesOrderService {
    List<SalesOrder> getHistorySalesOrders(Long userId);
    Boolean createSalesOrder(@RequestBody SalesOrder salesOrder, DeliveryOption deliveryOption, User user);
    Optional<SalesOrder> salesOrderDetail(Long salesOrderId);
    String getSalesOrderStatus(Long salesOrderId);
    void updateSalesOrderStatus(Long salesOrderId, String status);
    List<DeliveryOption> calculatePrice(@RequestBody SalesOrder salesOrder);


//    void setSalesOrderStatus(String status);
}