package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.enity.Order;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.OrderRepository;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import AIDeliver.com.example.AIDeliver.tools.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;

    }

    public List<Order> getHistorySalesOrders(Long id) {
        return orderRepository.findSalesOrderByUserId(id);
    }

    public Boolean createSalesOrder(Order order, DeliveryOption deliveryOption, User user) {
        //order.delivererId = delivererId
        Optional<Order> salesOrderOptional = orderRepository.findSalesOrderById(order.getId());
        if (salesOrderOptional.isPresent()) {
            throw new IllegalStateException("order already existed");
        }
        order.setPaymentAmount(deliveryOption.priceOption);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.InProgress.toString());

//        order.setSalesOrderStatus("order created");
//        order.setDelivererId(deliverer.getId());
        orderRepository.save(order);
        return true;
        }

    public Optional<Order> salesOrderDetail(Long salesOrderId) {
        return orderRepository.findSalesOrderById(salesOrderId);
    }

    public String getSalesOrderStatus(Long salesOrderId) {
        Optional<Order> orderOptional = orderRepository.findSalesOrderById(salesOrderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalStateException("order does not exist");
        }
        return orderRepository.findSalesOrderStatusById(salesOrderId);
    }

    public void updateSalesOrderStatus(Long salesOrderId, String status) {

            Order existingOrder = orderRepository.findById(salesOrderId)
                    .orElseThrow(() -> new IllegalStateException(
                            "order with id " + salesOrderId + " does not exist."
                    ));
            existingOrder.setOrderStatus(status);
            orderRepository.save(existingOrder);
        }

    public List<DeliveryOption> calculatePrice(Order order) {
        //sender zipcode-receiver zip code
        String key = order.getSenderZipCode() + order.getReceiverZipCode();

        ZipCode zipCode = new ZipCode();
        Coordinate coordinate = new Coordinate(order.getSenderZipCode(), order.getReceiverZipCode());

        double paymentAmount1 = order.getWeight() * zipCode.calTable.get(coordinate) * 20; // 20 needs to be updated drone
        double paymentAmount2 = order.getWeight() * zipCode.calTable.get(coordinate) * 10; // 10 needs to be updated robot

        List<DeliveryOption> list = new ArrayList<>();
        DeliveryOption drone = new DeliveryOption();
        drone.priceOption = paymentAmount1;
        drone.type = "drone";

        DeliveryOption robot = new DeliveryOption();
        robot.priceOption = paymentAmount2;
        robot.type = "robot";

        list.add(drone);
        list.add(robot);

        return list;
    }
    }
