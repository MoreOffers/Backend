package AIDeliver.com.example.AIDeliver.repository;


import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DelivererRepository extends JpaRepository<Deliverer, Long> {
   // Optional<Deliverer> findDelivererByEquipId(String equip_id);
    Optional<Deliverer> findDelivererById(Long deliverer_id);
}

