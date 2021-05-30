package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.common.util.Constant;
import AIDeliver.com.example.AIDeliver.dto.*;
import AIDeliver.com.example.AIDeliver.dto.request.OrderHistoryRequest;
import AIDeliver.com.example.AIDeliver.dto.request.OrderInfoRequest;
import AIDeliver.com.example.AIDeliver.dto.request.OrderTrackingRequest;
import AIDeliver.com.example.AIDeliver.dto.response.OrderHistoryResponse;
import AIDeliver.com.example.AIDeliver.dto.response.OrderTrackingResponse;
import AIDeliver.com.example.AIDeliver.dto.response.Selected;
import AIDeliver.com.example.AIDeliver.dto.response.StationStatus;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
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
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/placeOrderConfirm")
    public String placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        OrderDTO orderDTO = placeOrderDTO.getOrderInfo();
        UserDTO userDTO = placeOrderDTO.getUser();
        SelectedDTO selectedDTO = placeOrderDTO.getSelected();
        String trackingNumer = orderService.createOrder(orderDTO, userDTO, selectedDTO);
        return trackingNumer;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/historyOrder")
    public OrderHistoryDTO findAllOrders(@RequestBody UserDTO userDTO){
        String email = userDTO.getEmail();
        return orderService.getHistorySalesOrdersByEmail(email);
    }

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

