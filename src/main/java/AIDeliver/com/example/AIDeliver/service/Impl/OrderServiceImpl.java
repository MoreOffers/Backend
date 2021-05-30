package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.common.util.OrderStatus;
import AIDeliver.com.example.AIDeliver.dto.OrderDTO;
import AIDeliver.com.example.AIDeliver.dto.PlaceOrderDTO;
import AIDeliver.com.example.AIDeliver.dto.SelectedDTO;
import AIDeliver.com.example.AIDeliver.dto.UserDTO;
import AIDeliver.com.example.AIDeliver.dto.request.OrderConfirmationRequest;
import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.DelivererRepository;
import AIDeliver.com.example.AIDeliver.repository.OrderRepository;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DelivererRepository delivererRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;

    }

    @Override
    public String createOrder(OrderDTO orderDTO, UserDTO userDTO, SelectedDTO selectedDTO) {
        String trackingNumber = generateTrackingNumber();
        orderRepository.save(modelMapper.map(orderDTO, Orders.class));
        Deliverer deliverer = new Deliverer();
        deliverer.setType(selectedDTO.getType());
//        Deliverer deliverer = delivererRepository.save(modelMapper.map(selected, Deliverer.class));
        userRepository.save(modelMapper.map(userDTO, User.class));
        return trackingNumber;
    }

    @Override
    public List<Orders> getHistorySalesOrders(Long userId) {
        return orderRepository.findSalesOrderByUserId(userId);
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString().substring(0, 15);
    }

}
