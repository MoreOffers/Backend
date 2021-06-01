package AIDeliver.com.example.AIDeliver.dto;

import AIDeliver.com.example.AIDeliver.enity.Orders;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderHistoryDTO {
    private Map<String, List<Orders>> pending;
    private Map<String, List<Orders>> completed;
}
