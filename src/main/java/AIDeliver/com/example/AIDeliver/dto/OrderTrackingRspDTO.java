package AIDeliver.com.example.AIDeliver.dto;
import lombok.Data;

import java.util.Map;

@Data
public class OrderTrackingRspDTO {
    private String trackingNumber;
    private String orderStatus;
    private String createTime;
    private String updateTime;
    private String arriveTime;
    private Map<String, DeliverStatusDTO> delivererPath;

}
