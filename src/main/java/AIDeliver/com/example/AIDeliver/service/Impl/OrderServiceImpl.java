package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.common.util.OrderStatus;
import AIDeliver.com.example.AIDeliver.dto.*;
import AIDeliver.com.example.AIDeliver.dto.request.OrderConfirmationRequest;
import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.DelivererRepository;
import AIDeliver.com.example.AIDeliver.repository.OrderRepository;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

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

    @Transactional
    @Override
    public String createOrder(OrderDTO orderDTO, UserDTO userDTO, SelectedDTO selectedDTO) {
        String trackingNumber = generateTrackingNumber();
        orderDTO.setUser(modelMapper.map(userDTO, User.class));
        orderDTO.setDeliverer(modelMapper.map(selectedDTO, Deliverer.class));
        orderRepository.save(modelMapper.map(orderDTO, Orders.class));
        return trackingNumber;
    }

    @Override
    public OrderHistoryDTO getHistorySalesOrdersByEmail(String email) {
        OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO();

        List<Orders> pendingList = new ArrayList<>();
        List<Orders> completedList = new ArrayList<>();

        Long id = userRepository.findUserByEmail(email).getId();
        List<Orders> orders = orderRepository.findSalesOrderByUserId(id);

        for (Orders order : orders) {
            String status = order.getStatus();
            if (status.toLowerCase() != "completed") {
                pendingList.add(order);
            } else {
                completedList.add(order);
            }
        }
        Map<String, List<Orders>> pendingMap = new HashMap<>();
        pendingMap.put("pending", pendingList);
        orderHistoryDTO.setPending(pendingMap);


        Map<String, List<Orders>> completeMap = new HashMap<>();
        pendingMap.put("completed", completedList);
        orderHistoryDTO.setPending(completeMap);

        return orderHistoryDTO;
    }

    @Override
    public List<Orders> getHistorySalesOrders(Long userId) {
        return orderRepository.findSalesOrderByUserId(userId);
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString().substring(0, 15);
    }

}
