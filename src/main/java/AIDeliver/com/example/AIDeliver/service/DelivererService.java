package AIDeliver.com.example.AIDeliver.service;

import AIDeliver.com.example.AIDeliver.dto.OrderDTO;
import AIDeliver.com.example.AIDeliver.dto.OrderQuoteRspDTO;
import AIDeliver.com.example.AIDeliver.dto.OrderTrackingRspDTO;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import org.springframework.stereotype.Service;

@Service
public interface DelivererService {

    OrderQuoteRspDTO getOptionQuote(OrderDTO orderDTO);

    OrderTrackingRspDTO getTrackingInfo(Orders orders, String trackingNumebr);

}
