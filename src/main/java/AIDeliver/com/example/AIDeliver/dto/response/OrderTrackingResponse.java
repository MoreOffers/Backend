package AIDeliver.com.example.AIDeliver.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class OrderTrackingResponse {
    private Map<String, StationStatus> delivererPath;
}
