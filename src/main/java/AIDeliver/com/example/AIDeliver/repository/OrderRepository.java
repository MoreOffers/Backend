package AIDeliver.com.example.AIDeliver.repository;

import AIDeliver.com.example.AIDeliver.enity.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {

//    Orders createOrder(OrderInfoRequest orderInfo, Selected option, User user);

//    List<Orders> findSalesOrdersByUserId(User userId);
//
//    Optional<Orders> findSalesOrderById(Long salesOrderId);
//
//    String findSalesOrderStatusById(Long salesOrderId);

    List<Orders> findSalesOrderByUserId(Long id);

}