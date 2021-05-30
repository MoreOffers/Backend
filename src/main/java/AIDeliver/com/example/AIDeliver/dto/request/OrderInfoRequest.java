package AIDeliver.com.example.AIDeliver.dto.request;

import lombok.Data;

import java.util.Map;

@Data
public class OrderInfoRequest {
 //   private Map<String, String> orderInfoRequest;
    private String from;
    private String to;
    private String fromAddress;
    private String toAddress;
    private String weight;
    private String size;
    private String time;
}
