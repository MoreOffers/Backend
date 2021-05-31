package AIDeliver.com.example.AIDeliver.service;

import AIDeliver.com.example.AIDeliver.dto.OrderDTO;
import AIDeliver.com.example.AIDeliver.dto.OrderQuoteRspDTO;
import AIDeliver.com.example.AIDeliver.enity.Station;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DelivererService {

    String getCurPosition(String type, List<Station> stations, int senderZip, int receiverZip, String createTime, String curTime);

    OrderQuoteRspDTO getOptionQuote(OrderDTO orderDTO);

}
