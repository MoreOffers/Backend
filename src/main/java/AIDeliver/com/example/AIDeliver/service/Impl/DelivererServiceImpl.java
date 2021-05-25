package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.dto.request.OrderInfoRequest;
import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.enity.Station;

import AIDeliver.com.example.AIDeliver.repository.DelivererRepository;
import AIDeliver.com.example.AIDeliver.service.DelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DelivererServiceImpl implements DelivererService {

    private final DelivererRepository delivererRepository;

    @Autowired
    public DelivererServiceImpl(DelivererRepository delivererRepository) {
        this.delivererRepository = delivererRepository;
    }

    public Optional<Deliverer> findDelivererById(Long deliverer_id) {
        return delivererRepository.findById(deliverer_id);
    }


    public String tracking(List<Station> stations, int zipcode) {
        int min = Integer.MAX_VALUE;
        String address = "";
        for(Station station: stations) {
            int close = Math.abs(station.getZipcode() - zipcode);
            if (close < min) {
                address = station.getStation_address();
                min = close;
            }
        }

        return address;
    }


/*for test
public String tracking(List<Station> stations, int zipcode) {
    int min = Integer.MAX_VALUE;
    String address = "";
    for(Station station: stations) {
        int close = Math.abs(station.getZipCode() - zipcode);
        if (close < min) {
            address = station.getAddress();
            min = close;
        }
    }

    return address;
}

*/
    public String getCurPosition(String type, List<Station> stations, int senderZip, int receiverZip, String createTime, String curTime) {
        int start = Integer.valueOf(createTime);
        int end = Integer.valueOf(curTime);

        if(type == "drone") {
            if (end - start <= 3) {
                return "Drone is on the way to pick up your package";
            } else if (end - start <= 6) {
                return "Your package is on the way";
            } else if (end - start <= 8) {
                return tracking(stations, senderZip);
            } else if (end - start <= 11) {
                return tracking(stations, receiverZip);
            }
        }

        else if (type == "robot") {
            if (end - start <= 0) {
                end += 24;
            }
            if (end - start <= 5) {
                return "Robot is on the way to pick up your package";
            } else if (end - start <= 10) {
                return "Your package is on the way";
            } else if (end - start <= 15) {
                return tracking(stations, senderZip);
            } else if (end - start <= 22) {
                return tracking(stations, receiverZip);
            }
        }

        return "Your package has been delivered";

    }

    @Override
    public Double getRobotEstimatePrice(OrderInfoRequest orderInfoRequest) {
        return 100.0;
    }

    @Override
    public Double getDroneEstimatePrice(OrderInfoRequest orderInfoRequest) {
        return 200.0;
    }


}



