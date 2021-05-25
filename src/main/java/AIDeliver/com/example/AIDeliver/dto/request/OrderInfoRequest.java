package AIDeliver.com.example.AIDeliver.dto.request;

import lombok.Data;

@Data
public class OrderInfoRequest {
    private String from;
    private String to;
    private String weight;
    private String size;
    private String time;
}
