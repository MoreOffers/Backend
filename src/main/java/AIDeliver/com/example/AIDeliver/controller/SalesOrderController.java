package AIDeliver.com.example.AIDeliver.controller;


import AIDeliver.com.example.AIDeliver.enity.Order;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.Impl.DeliveryOption;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "salesOrder")

public class SalesOrderController {

    @Autowired
    private OrderService salesOrderService;

    @Autowired
    private UserService userService;


    @PostMapping(path = "/order/placeOrderQuote")
    public ResponseEntity<Object> quote(@RequestBody Order order) {

        ResponseEntity<Object> response = null;

//            Boolean isSuccess = userService.addNewUser(user);

        List<DeliveryOption> deliveryOptions = salesOrderService.calculatePrice(order);
        response = new ResponseEntity(deliveryOptions, HttpStatus.OK);
        return response;
    }

    @PostMapping(path = "/order/placeOrderConfirm")
    public ResponseEntity<Object> confirmOrder(@RequestBody Order order, DeliveryOption deliveryOption, User user) {

        ResponseEntity<Object> response = null;

        salesOrderService.createSalesOrder(order, deliveryOption, user);
        Boolean isSuccess = salesOrderService.createSalesOrder(order, deliveryOption, user);
        response = new ResponseEntity(order.getTrackingNumber(), HttpStatus.OK);
        return response;


    }
}

