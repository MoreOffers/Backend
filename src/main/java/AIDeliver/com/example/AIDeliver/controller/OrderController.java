package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.dto.*;
import AIDeliver.com.example.AIDeliver.dto.request.OrderTrackingRequest;
import AIDeliver.com.example.AIDeliver.dto.response.OrderTrackingResponse;
import AIDeliver.com.example.AIDeliver.dto.response.StationStatus;
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
    @GetMapping( value = { "/user/tracking","tracking" })
    public ResponseEntity<OrderTrackingResponse> OrderTracking (@RequestBody OrderTrackingRequest orderTrackingRequest){
        String trackingNumber = orderTrackingRequest.getTrackingNumber();
        OrderTrackingResponse orderTrackingResponse = new OrderTrackingResponse();
        Map<String, StationStatus> delivererPath = new HashMap<>();

        Date date = new Date();

        StationStatus stationStatus1 = new StationStatus();
        stationStatus1.setStatus("departed");
        stationStatus1.setDate(date);
        delivererPath.put("station1", stationStatus1);

        StationStatus stationStatus2 = new StationStatus();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        stationStatus1.setDate(date);
        delivererPath.put("station2", stationStatus1);

        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        StationStatus stationStatus3 = new StationStatus();
        stationStatus1.setDate(date);
        delivererPath.put("station3", stationStatus1);

        orderTrackingResponse.setDelivererPath(delivererPath);
        return new ResponseEntity(orderTrackingResponse, HttpStatus.OK);
    }

}

