package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.common.util.Constant;
import AIDeliver.com.example.AIDeliver.dto.request.OrderConfirmationRequest;
import AIDeliver.com.example.AIDeliver.dto.request.OrderHistoryRequest;
import AIDeliver.com.example.AIDeliver.dto.request.OrderInfoRequest;
import AIDeliver.com.example.AIDeliver.dto.response.OrderHistoryResponse;
import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.DelivererService;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DelivererService delivererService;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/placeOrderQuote")
    public ResponseEntity<List<Selected>> quote(@RequestBody OrderInfoRequest orderInfoRequest) {

        Double robotPrice = delivererService.getRobotEstimatePrice(orderInfoRequest);
        Double dronePrice = delivererService.getDroneEstimatePrice(orderInfoRequest);

        List<Selected> res = new ArrayList<>();

        Selected robotOpt = new Selected();
        robotOpt.setOption(Constant.Robot);
        robotOpt.setPrice(robotPrice);

        Selected droneOpt = new Selected();
        droneOpt.setOption(Constant.Drone);
        droneOpt.setPrice(dronePrice);

        res.add(robotOpt);
        res.add(droneOpt);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    // registered customer
    @PostMapping("/placeOrderConfirm")
    public String placeOrder(@RequestBody OrderConfirmationRequest orderConfirmationRequest){
        String trackingNumer = orderService.createOrder(orderConfirmationRequest);
        return trackingNumer;
    }


    @GetMapping("/historyOrder")
    public ResponseEntity<OrderHistoryResponse> findAllOrders(@RequestBody OrderHistoryRequest orderHistoryRequest){
        String email = orderHistoryRequest.getEmail();
        User user = userService.findUserByEmail(orderHistoryRequest.getEmail());
        List<Orders> orders = orderService.getHistorySalesOrders(user.getId());

        OrderHistoryResponse orderHistoryResponse = new OrderHistoryResponse();
        Map<String,List<Orders>> pendingOrders = new HashMap<>();
        Map<String,List<Orders>> completedOrders = new HashMap<>();

        pendingOrders.put("pending", new ArrayList<>());
        completedOrders.put("completed", new ArrayList<>());

        for (Orders order : orders) {
            String status = order.getOrderStatus();
            if (status == "pending") {
                pendingOrders.get("pending").add(order);
            } else {
                completedOrders.get("completed").add(order);
            }
        }

        orderHistoryResponse.setPending(pendingOrders);
        orderHistoryResponse.setCompleted(completedOrders);

        return new ResponseEntity<>(orderHistoryResponse, HttpStatus.OK);
    }

}

