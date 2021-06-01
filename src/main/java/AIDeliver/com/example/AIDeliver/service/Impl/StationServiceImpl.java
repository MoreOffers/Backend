package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.enity.Station;
import AIDeliver.com.example.AIDeliver.repository.StationRepository;
import AIDeliver.com.example.AIDeliver.service.StationService;

import java.util.List;

public class StationServiceImpl implements StationService {
    private StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }
}
