package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.dto.*;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.service.DelivererService;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DelivererService delivererService;

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/placeOrderQuote")
    public ResponseEntity<OrderQuoteRspDTO> quote(@RequestBody PlaceOrderDTO placeOrderDTO) {
        OrderQuoteRspDTO res = delivererService.getOptionQuote(placeOrderDTO.getOrderInfo());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user/placeOrderConfirm")
    public String userPlaceOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        OrderDTO orderDTO = placeOrderDTO.getOrderInfo();
        UserDTO userDTO = placeOrderDTO.getUser();
        SelectedDTO selectedDTO = placeOrderDTO.getSelected();
        String trackingNumer = orderService.registerUserCreateOrder(orderDTO, userDTO, selectedDTO);
        return trackingNumer;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/placeOrderConfirm")
    public String placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        OrderDTO orderDTO = placeOrderDTO.getOrderInfo();
        UserDTO userDTO = placeOrderDTO.getUser();
        SelectedDTO selectedDTO = placeOrderDTO.getSelected();
        String trackingNumer = orderService.visitorCreateOrder(orderDTO, userDTO, selectedDTO);
        return trackingNumer;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/historyOrder")
    public OrderHistoryDTO findAllOrders(@RequestBody UserDTO userDTO){
        String email = userDTO.getEmail();
        return orderService.getHistorySalesOrdersByEmail(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping( value = { "/tracking" })
    public OrderTrackingRspDTO OrderTracking (@RequestBody Map<String, String> trackingrequest){

        String trackingNumber = trackingrequest.get("trackingNumber");
        System.out.println(trackingNumber + "controller");
        OrderTrackingRspDTO orderTrackingRspDTO;

        Orders orders = orderService.getSalesOrderBytrackingNumber(trackingNumber);

        orderTrackingRspDTO = delivererService.getTrackingInfo(orders,trackingNumber);
        System.out.println(orderTrackingRspDTO.getTrackingNumber()+"sadfsdafsadf");

        return orderTrackingRspDTO;
    }

}

