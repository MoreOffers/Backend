package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.common.util.Constant;
import AIDeliver.com.example.AIDeliver.dto.request.OrderInfoRequest;
import AIDeliver.com.example.AIDeliver.dto.response.Option;
import AIDeliver.com.example.AIDeliver.enity.Order;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.DelivererService;
import AIDeliver.com.example.AIDeliver.service.Impl.DeliveryOption;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DelivererService delivererService;

    @PostMapping(path = "/placeOrderQuote")
    public ResponseEntity<List<Option>> quote(@RequestBody OrderInfoRequest orderInfoRequest) {

        Double robotPrice = delivererService.getRobotEstimatePrice(orderInfoRequest);
        Double dronePrice = delivererService.getDroneEstimatePrice(orderInfoRequest);

        List<Option> res = new ArrayList<>();

        Option robotOpt = new Option();
        robotOpt.setName(Constant.Robot);
        robotOpt.setPrice(robotPrice);

        Option droneOpt = new Option();
        droneOpt.setName(Constant.Drone);
        droneOpt.setPrice(dronePrice);

        res.add(robotOpt);
        res.add(droneOpt);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PostMapping(path = "/order/placeOrderConfirm")
    public ResponseEntity<Object> confirmOrder(@RequestBody Order order, DeliveryOption deliveryOption, User user) {

        ResponseEntity<Object> response = null;

        orderService.createSalesOrder(order, deliveryOption, user);
        Boolean isSuccess = orderService.createSalesOrder(order, deliveryOption, user);
        response = new ResponseEntity(order.getTrackingNumber(), HttpStatus.OK);
        return response;


    }
}

