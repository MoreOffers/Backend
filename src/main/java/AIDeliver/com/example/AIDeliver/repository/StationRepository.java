package AIDeliver.com.example.AIDeliver.repository;

import AIDeliver.com.example.AIDeliver.enity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findStationByStationAddress(String stationAddress);
}
