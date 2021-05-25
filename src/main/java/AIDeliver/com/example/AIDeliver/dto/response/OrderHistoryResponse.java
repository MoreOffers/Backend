package AIDeliver.com.example.AIDeliver.dto.response;

import AIDeliver.com.example.AIDeliver.enity.Orders;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderHistoryResponse {
    private Map<String,List<Orders>> pendingOrders;
    private Map<String,List<Orders>> completedOrders;
}
