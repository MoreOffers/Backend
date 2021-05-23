package AIDeliver.com.example.AIDeliver.controller;

import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Station;
import AIDeliver.com.example.AIDeliver.enity.Order;

import AIDeliver.com.example.AIDeliver.service.DelivererService;
import AIDeliver.com.example.AIDeliver.service.OrderService;
import AIDeliver.com.example.AIDeliver.service.StationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping(path = "deliverer")
public class DelivererController {
        @Autowired
        private DelivererService delivererService;

        @Autowired
        private OrderService orderService;

        @Autowired
        private StationService stationService;

        @GetMapping("/tracking")
        //public ResponseEntity<Object> trackingOrder(){
        public ResponseEntity<Object> trackingOrder(@RequestParam(value = "order_number") String order_number) {

            Order curOrder = orderService.findOrderByOrderNumber(orde_number);
            long deliver_id = curOrder.getDelivererId();
            int senderZip = curOrder.getSenderZipcode();
            int receiverZip = curOrder.getReceiverZipcode();
            String createTime = curOrder.getCreateTime().toString().substring(11,13);

            String type = "";
            Optional<Deliverer> curDeliverer = delivererService.findDelivererByDelivererId(deliver_id);
            if (curDeliverer. isPresent()) {
                Deliverer myDeliverer = curDeliverer.get();
                 type = myDeliverer.getType();
            }

            String curTime = LocalTime.now().toString().substring(11,13);

            List<Station> stations = stationService.getAllStations();


            /*  test
            String type1 = "robot";
            Station one = new Station("stationA",95132);

            Station two = new Station("stationB", 95154);

            Station three = new Station("stationC", 96100);

            List<Station> stations= new ArrayList<>();
            stations.add(one);
            stations.add(two);
            stations.add(three);
            int sender = 95172;
            int receiver = 95183;
            String createTime = "16";
            String curTime = "3";

            String curPos =  delivererService.getCurPosition(type1, stations, sender, receiver, createTime, curTime);
            System.out.println(curPos);
            */

            String curPos =  delivererService.getCurPosition(type, stations, senderZip, receiverZip, createTime, curTime);

            return new ResponseEntity<>(curPos, HttpStatus.OK);
        }


}








