package AIDeliver.com.example.AIDeliver.dto.request;

import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.enity.User;
import lombok.Data;

@Data
public class OrderConfirmationRequest {
    private OrderInfoRequest orderInfo;
    private Selected selected;
    private User user;
}
