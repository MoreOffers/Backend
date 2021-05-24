package AIDeliver.com.example.AIDeliver.repository;

import AIDeliver.com.example.AIDeliver.enity.SalesOrder;
import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findSalesOrdersByUserId(User userId);

    Optional<SalesOrder> findSalesOrderById(Long salesOrderId);

    String findSalesOrderStatusById(Long salesOrderId);

    List<SalesOrder> findSalesOrderByUserId(Long id);

}