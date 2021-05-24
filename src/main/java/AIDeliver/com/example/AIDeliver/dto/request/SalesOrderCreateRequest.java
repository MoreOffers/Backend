package AIDeliver.com.example.AIDeliver.dto.request;

import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.Impl.DeliveryOption;
import lombok.Data;

@Data

public class SalesOrderCreateRequest {
    private Long userId;
    private String senderZipCode;
    private String receiverZipCode;
    private double weight;
    private double size;
    private DeliveryOption deliveryOption;

}
