package AIDeliver.com.example.AIDeliver.repository;


import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DelivererRepository extends JpaRepository<Deliverer, Long> {
//    Deliverer findDelivererByType(String type);
//    Deliverer findAvailableDeliverer(Boolean is_available);
    List<Deliverer> findAll();
}

