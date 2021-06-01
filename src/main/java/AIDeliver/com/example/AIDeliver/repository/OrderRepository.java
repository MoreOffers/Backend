package AIDeliver.com.example.AIDeliver.repository;

import AIDeliver.com.example.AIDeliver.enity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findSalesOrderByUserId(Long id);
    Orders findOrdersByTrackingNumber(String trackingNumber);
    Orders findOrdersById(Long id);
}