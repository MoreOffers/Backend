package AIDeliver.com.example.AIDeliver.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderQuoteRstDTO {
    private Map<String, OrderDTO> orderDTO;
}
