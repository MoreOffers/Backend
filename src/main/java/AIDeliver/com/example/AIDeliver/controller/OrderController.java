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

    @PostMapping(path = "/placeOrderQuote")
    public ResponseEntity<OrderQuoteRspDTO> quote(@RequestBody PlaceOrderDTO placeOrderDTO) {
        OrderQuoteRspDTO res = delivererService.getOptionQuote(placeOrderDTO.getOrderInfo());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value={"/user/placeOrderConfirm", "/placeOrderConfirm"})
    public String userPlaceOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        OrderDTO orderDTO = placeOrderDTO.getOrderInfo();
        UserDTO userDTO = placeOrderDTO.getUser();
        SelectedDTO selectedDTO = placeOrderDTO.getSelected();
        String trackingNumer = orderService.createOrder(orderDTO, userDTO, selectedDTO);
        return trackingNumer;
    }

    @GetMapping(path = "/historyOrder")
    public ResponseEntity<OrderHistoryDTO> findAllOrders(@RequestBody UserDTO userDTO){
        String email = userDTO.getEmail();
        return new ResponseEntity<>(orderService.getHistorySalesOrdersByEmail(email), HttpStatus.OK);
    }

    @GetMapping( value = { "/user/tracking", "/tracking" })
    public ResponseEntity<OrderTrackingRspDTO> OrderTracking (@RequestBody OrderTrackingRstDTO orderTrackingRstDTO){

        String trackingNumber = orderTrackingRstDTO.getTrackingNumber();
        OrderTrackingRspDTO orderTrackingRspDTO;

        Orders orders = orderService.getSalesOrderBytrackingNumber(trackingNumber);

        orderTrackingRspDTO = delivererService.getTrackingInfo(orders,trackingNumber);

        return new ResponseEntity<>(orderTrackingRspDTO, HttpStatus.OK);
    }

}

