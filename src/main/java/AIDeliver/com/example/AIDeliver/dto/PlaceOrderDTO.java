package AIDeliver.com.example.AIDeliver.dto;

import lombok.Data;

@Data
public class PlaceOrderDTO {
    private OrderDTO orderInfo;
    private SelectedDTO selected;
    private UserDTO user;
}
