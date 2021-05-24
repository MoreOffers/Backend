package AIDeliver.com.example.AIDeliver.service;

import AIDeliver.com.example.AIDeliver.enity.Station;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StationService {
    List<Station> getAllStations();
}
