package AIDeliver.com.example.AIDeliver.service;

import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Station;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DelivererService {

    String getCurPosition(String type, List<Station> stations, int senderZip, int receiverZip, String createTime, String curTime);
   // Optional<Deliverer> findDelivererByEquipId(String equip_id);
    Optional<Deliverer> findDelivererById(Long deliverer_id);


}
