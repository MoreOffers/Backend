package AIDeliver.com.example.AIDeliver.repository;

import AIDeliver.com.example.AIDeliver.enity.Order;
import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByUserId(Long userId);

    Optional<Order> findOrderById(Long orderId);

    String findOrderStatusById(Long orderId);



}