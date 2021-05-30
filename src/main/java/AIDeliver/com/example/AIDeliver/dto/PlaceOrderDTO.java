package AIDeliver.com.example.AIDeliver.dto;

import AIDeliver.com.example.AIDeliver.dto.request.OrderInfoRequest;
import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import lombok.Data;

import java.util.Map;

@Data
public class PlaceOrderDTO {
    private OrderDTO orderInfo;
    private SelectedDTO selected;
    private UserDTO user;
}
