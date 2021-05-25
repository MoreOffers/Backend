package AIDeliver.com.example.AIDeliver.dto.request;

import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.Impl.DeliveryOption;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoRequest {
    private String from;
    private String to;
    private String weight;
    private String size;
    private String time;
}
