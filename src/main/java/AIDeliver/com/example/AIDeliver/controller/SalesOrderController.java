package AIDeliver.com.example.AIDeliver.controller;


import AIDeliver.com.example.AIDeliver.dto.request.SalesOrderCreateRequest;
import AIDeliver.com.example.AIDeliver.enity.SalesOrder;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.service.Impl.DeliveryOption;
import AIDeliver.com.example.AIDeliver.service.SalesOrderService;
import AIDeliver.com.example.AIDeliver.service.SecurityService;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "salesOrder")

public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private UserService userService;


    @PostMapping(path = "/order/placeOrderQuote")
    public ResponseEntity<Object> quote(@RequestBody SalesOrder salesOrder) {

        ResponseEntity<Object> response = null;

//            Boolean isSuccess = userService.addNewUser(user);

        List<DeliveryOption> deliveryOptions = salesOrderService.calculatePrice(salesOrder);
        response = new ResponseEntity(deliveryOptions, HttpStatus.OK);
        return response;
    }

    @PostMapping(path = "/order/placeOrderConfirm")
    public ResponseEntity<Object> confirmOrder(@RequestBody SalesOrder salesOrder, DeliveryOption deliveryOption, User user) {

        ResponseEntity<Object> response = null;

        salesOrderService.createSalesOrder(salesOrder, deliveryOption, user);
        Boolean isSuccess = salesOrderService.createSalesOrder(salesOrder, deliveryOption, user);
        response = new ResponseEntity(salesOrder.getTrackingNumber(), HttpStatus.OK);
        return response;


    }
}

