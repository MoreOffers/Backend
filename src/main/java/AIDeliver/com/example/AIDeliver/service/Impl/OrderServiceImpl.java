package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.dto.request.OrderConfirmationRequest;
import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.OrderRepository;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import AIDeliver.com.example.AIDeliver.tools.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;

    }


//
//    public List<Orders> getHistorySalesOrders(Long id) {
//        return orderRepository.findSalesOrderByUserId(id);
//    }

//    @Override
//    public String createOrder(OrderInfoRequest orderInfo, Selected option, User user) {
//
//        Orders order = orderRepository.createOrder(orderInfo, option, user);
//
//        String startPostion = orderInfo.getFrom();
//        String endPostion = orderInfo.getTo();
//        String weightStr = orderInfo.getWeight().trim();
//        // later will change function to be more flexible, Kg, g, etc
//        Double weight = Double.parseDouble(weightStr.substring(0, weightStr.length() - 2));
//        // size, later will have function to map actual size from String
//        String size = "Small";
//
//        String shippingMethod = option.getName();
//        Double price = option.getPrice();
//
//        order.setPaymentAmount(price);
//
//        order.setSenderAddress(startPostion);
//        order.setReceiverAddress(endPostion);
//        order.setWeight(weight);
//        order.setSize(size);
//        order.setOrderStatus(OrderStatus.InProgress.toString());
//
//        order.setUser(user);
//
//        byte[] array = new byte[16]; // length is bounded by 7
//        new Random().nextBytes(array);
//        String trackingNumber = new String(array, Charset.forName("UTF-8"));
//
//        order.setTrackingNumber(trackingNumber);
//
//        return trackingNumber;
//    }



//    public Boolean createSalesOrder(Orders order, DeliveryOption deliveryOption, User user) {
//        //order.delivererId = delivererId
//        Optional<Orders> salesOrderOptional = orderRepository.findSalesOrderById(order.getId());
//        if (salesOrderOptional.isPresent()) {
//            throw new IllegalStateException("order already existed");
//        }
//        order.setPaymentAmount(deliveryOption.priceOption);
//        order.setUser(user);
//        order.setOrderStatus(OrderStatus.InProgress.toString());
//
////        order.setSalesOrderStatus("order created");
////        order.setDelivererId(deliverer.getId());
//        orderRepository.save(order);
//        return true;
//        }

//    public Optional<Orders> salesOrderDetail(Long salesOrderId) {
//        return orderRepository.findSalesOrderById(salesOrderId);
//    }

//    public String getSalesOrderStatus(Long salesOrderId) {
//        Optional<Orders> orderOptional = orderRepository.findSalesOrderById(salesOrderId);
//        if (orderOptional.isEmpty()) {
//            throw new IllegalStateException("order does not exist");
//        }
//        return orderRepository.findSalesOrderStatusById(salesOrderId);
//    }

//    public void updateSalesOrderStatus(Long salesOrderId, String status) {
//
//            Orders existingOrder = orderRepository.findById(salesOrderId)
//                    .orElseThrow(() -> new IllegalStateException(
//                            "order with id " + salesOrderId + " does not exist."
//                    ));
//            existingOrder.setOrderStatus(status);
//            orderRepository.save(existingOrder);
//        }

//    public List<DeliveryOption> calculatePrice(Orders orders) {
//        //sender zipcode-receiver zip code
//        String key = orders.getSenderZipCode() + orders.getReceiverZipCode();
//
//        ZipCode zipCode = new ZipCode();
//        Coordinate coordinate = new Coordinate(orders.getSenderZipCode(), orders.getReceiverZipCode());
//
//        double paymentAmount1 = orders.getWeight() * zipCode.calTable.get(coordinate) * 20; // 20 needs to be updated drone
//        double paymentAmount2 = orders.getWeight() * zipCode.calTable.get(coordinate) * 10; // 10 needs to be updated robot
//
//        List<DeliveryOption> list = new ArrayList<>();
//        DeliveryOption drone = new DeliveryOption();
//        drone.priceOption = paymentAmount1;
//        drone.type = "drone";
//
//        DeliveryOption robot = new DeliveryOption();
//        robot.priceOption = paymentAmount2;
//        robot.type = "robot";
//
//        list.add(drone);
//        list.add(robot);
//
//        return list;
//    }

    @Override
    public String createOrder(OrderConfirmationRequest orderConfirmationRequest) {
        String trackingNumber = generateTrackingNumber();
        saveOrder(orderConfirmationRequest, trackingNumber);
        return trackingNumber;
    }

    private void saveOrder(OrderConfirmationRequest orderConfirmationRequest, String trackingNumber) {
        Orders orders = new Orders();
        String senderAddress = orderConfirmationRequest.getOrderInfo().getFrom();
        String receiverAddress = orderConfirmationRequest.getOrderInfo().getTo();
        String weightStr = orderConfirmationRequest.getOrderInfo().getWeight().trim();
        Double weight = Double.parseDouble(weightStr.substring(0, weightStr.length() - 2));
        String size = "Small";
//        String delivererType = orderConfirmationRequest.getSelected().getOption();
        Double price = orderConfirmationRequest.getSelected().getPrice();
//        Deliverer deliverer = new Deliverer();
//        deliverer.setType(delivererType);
//        userRepository.save(orderConfirmationRequest.getUser());

        User user = userRepository.findUserByEmail(orderConfirmationRequest.getUser().getEmail());

//        if (user == null) {
//            User newU
//            userRepository.save(user);
//        }

        orders.setSenderAddress(senderAddress);
        orders.setReceiverAddress(receiverAddress);
        orders.setWeight(weight);
        orders.setSize(size);
        orders.setPaymentAmount(price);
        orders.setUser(user);
        orderRepository.save(orders);
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString().substring(0, 15);
    }

}
