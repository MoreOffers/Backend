package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Order;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.OrderRepository;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getHistoryOrders(Long userId) {
        return orderRepository.findOrderByUserId(userId);
    }

    public void createOrder(Order order, Deliverer deliverer) {
        //order.delivererId = delivererId
        Optional<Order> orderOptional = orderRepository.findOrderById(order.getId());
        if (orderOptional.isPresent()) {
            throw new IllegalStateException("order already existed");
        }
        order.setPaymentAmount(deliverer.getPrice());
        order.setOrderStatus("order created");
        order.setDelivererId(deliverer.getId());
        orderRepository.save(order);
        }

    public Optional<Order> orderDetail(Long orderId) {
        return orderRepository.findOrderById(orderId);
    }

    public String getOrderStatus(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findOrderById(orderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalStateException("order does not exist");
        }
        return orderRepository.findOrderStatusById(orderId);
    }

    public void updateOrderStatus(Long orderId, String status) {

            Order existingOrder = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalStateException(
                            "order with id " + orderId + " does not exist."
                    ));
            existingOrder.setOrderStatus(status);
            orderRepository.save(existingOrder);
        }

    }
