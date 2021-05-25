package AIDeliver.com.example.AIDeliver.repository;

import AIDeliver.com.example.AIDeliver.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
    User findUserByName(String name);

//    @Query("SELECT new com.javatechie.jpa.dto.OrderResponse(c.name , p.productName) FROM Customer c JOIN c.products p")
//    @Query("SELECT new com.javatechie.jpa.dto.OrderResponse(u.name , o.productName) FROM User u JOIN u.orders o")

//    List<Orders> getCustomerOrders(String email);

}
