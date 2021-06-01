package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.common.util.Constant;
import AIDeliver.com.example.AIDeliver.dto.*;
import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.Station;

import AIDeliver.com.example.AIDeliver.repository.DelivererRepository;
import AIDeliver.com.example.AIDeliver.repository.StationRepository;
import AIDeliver.com.example.AIDeliver.service.DelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DelivererServiceImpl implements DelivererService {

    private final DelivererRepository delivererRepository;
    private final StationRepository stationRepository;

    @Autowired
    public DelivererServiceImpl(DelivererRepository delivererRepository, StationRepository stationRepository) {
        this.delivererRepository = delivererRepository;
        this.stationRepository = stationRepository;
    }

    public Optional<Deliverer> findDelivererById(Long deliverer_id) {
        return delivererRepository.findById(deliverer_id);
    }

    @Override
    public OrderQuoteRspDTO getOptionQuote(OrderDTO orderDTO) {
        OrderQuoteRspDTO orderQuoteRspDTO = new OrderQuoteRspDTO();
        SelectedDTO selectedDTORobot = buildOption(Constant.ROBOT, orderDTO);
        SelectedDTO selectedDTODrone = buildOption(Constant.DRONE, orderDTO);
        orderQuoteRspDTO.setRobot(selectedDTORobot);
        orderQuoteRspDTO.setDrone(selectedDTODrone);
        return orderQuoteRspDTO;
    }



    private SelectedDTO buildOption(String type, OrderDTO orderDTO) {
        SelectedDTO selectedDTO = new SelectedDTO();
        selectedDTO.setType(type);
        selectedDTO.setPrice(getEstimatePrice(type, orderDTO));
        return selectedDTO;
    }

    private double getEstimatePrice(String type, OrderDTO orderDTO) {

        String weightStr = orderDTO.getWeight().trim();
        String sizeStr = orderDTO.getSize().trim();

        Double weight = Double.parseDouble(weightStr.substring(0, weightStr.length() - 2));
        Double size = Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 3));

        Double base = weight * size;
        return type == Constant.ROBOT ? base * 10 : base * 20;
    }


    public String tracking(List<Station> stations, int zipcode) {
        int min = Integer.MAX_VALUE;
        String address = "";
        for(Station station: stations) {
            int close = Math.abs(station.getZipcode() - zipcode);
            if (close < min) {
                address = station.getStation_address();
                min = close;
            }
        }

        return address;
    }



    public Map<String, DeliverStatusDTO> getDelivererCurPath(List<Station> stations, Orders orders) {
        Deliverer deliverer = orders.getDeliverer();
        String type = deliverer.getType();

        int[] gapArray;
        int gap;

        if (type == "Drone") {
            gapArray = new int[]{3, 6, 9};
            gap = 3;
        } else {
            gapArray = new int[]{5, 10, 15};
            gap = 5;
        }

        int senderZip = Integer.valueOf(orders.getSenderZipCode());
        int receiverZip = Integer.valueOf(orders.getReceiverZipCode());

        String senderAddress = orders.getSenderAddress();
        String receiverAddress = orders.getReceiverAddress();

        String start = orders.getCreateTime();
        String time = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(Calendar.getInstance().getTime());
        String end = time.substring(0, 16);

        String startsub = start.substring(0, 13);
        String endsub = end.substring(0, 13);
        String[] startTime = startsub.split("-");

        int startH = Integer.valueOf(startTime[3]);
        int endH = Integer.valueOf(endsub.split("-")[3]);
        int startD = Integer.valueOf(startTime[2]);
        int endD = Integer.valueOf(endsub.split("-")[2]);

        //<status, address & time>
        Map<String, DeliverStatusDTO> result = new HashMap<>();
        String status1 = "Stage1";
        String status2 = "Stage2";
        String status3 = "Stage3";
        String status4 = "Stage4";

        DeliverStatusDTO dStatus1 = new DeliverStatusDTO();
        DeliverStatusDTO dStatus2 = new DeliverStatusDTO();
        DeliverStatusDTO dStatus3 = new DeliverStatusDTO();
        DeliverStatusDTO dStatus4 = new DeliverStatusDTO();


        String addressA = tracking(stations, senderZip);
        String addressB = tracking(stations, receiverZip);

        if (endD != startD) {
            endH += 24;
        }

        if (endH - startH < gapArray[0]) {
            dStatus1.setTime(end);
            dStatus1.setAddress(senderAddress);
            result.put(status1, dStatus1);
        } else if (endH - startH < gapArray[1]) {
            if (result.containsKey(status1)) {
                result.remove(status1);
            } else {
                int temp = Integer.valueOf(startTime[3]) + gap;
                startTime[3] = String.valueOf(temp);
                String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";
                dStatus1.setTime(tempTime);
                dStatus1.setAddress(senderAddress);
                result.put(status1, dStatus1);
            }

            dStatus2.setTime(end);
            dStatus2.setAddress(addressA);
            result.put(status2, dStatus2);
        } else if (endH - startH < gapArray[2]) {
            if (result.containsKey(status1)) {
                result.remove(status1);
            } else {
                int temp = Integer.valueOf(startTime[3]) + gap;
                startTime[3] = String.valueOf(temp);
                String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";
                dStatus1.setTime(tempTime);
                dStatus1.setAddress(senderAddress);
                result.put(status1, dStatus1);
            }
            //stationOne
            if (result.containsKey(status2)) {
                result.remove(status2);
            } else {
                int temp = Integer.valueOf(startTime[3]) + gap;
                if (temp >= 24) {
                    temp = temp -24;
                    int tempDate = Integer.valueOf(startTime[2]) + 1;
                    startTime[2] = String.valueOf(tempDate);
                }
                startTime[3] = String.valueOf(temp);
                String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";
                dStatus2.setTime(tempTime);
                dStatus2.setAddress(addressA);
                result.put(status2, dStatus2);
            }

            //String address = tracking(stations, receiverZip);
            dStatus3.setTime(end);
            dStatus3.setAddress(addressB);
            result.put(status3, dStatus3);

        } else {
            if (result.containsKey(status1)) {
                result.remove(status1);
            } else {
                int temp = Integer.valueOf(startTime[3]) + gap;
                startTime[3] = String.valueOf(temp);
                String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";
                dStatus1.setTime(tempTime);
                dStatus1.setAddress(senderAddress);
                result.put(status1, dStatus1);
            }
            //stationOne
            if (result.containsKey(status2)) {
                result.remove(status2);
            } else {
                int temp = Integer.valueOf(startTime[3]) + gap;
                if (temp >= 24) {
                    temp = temp -24;
                    int tempDate = Integer.valueOf(startTime[2]) + 1;
                    startTime[2] = String.valueOf(tempDate);
                }
                startTime[3] = String.valueOf(temp);
                String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";
                dStatus2.setTime(tempTime);
                dStatus2.setAddress(addressA);
                result.put(status2, dStatus2);
            }
            //statoinTwo
            if (result.containsKey(status3)) {
                result.remove(status3);
            } else { //update time
                int temp = Integer.valueOf(startTime[3]) + gap;
                if (temp >= 24) {
                    temp = temp -24;
                    int tempDate = Integer.valueOf(startTime[2]) + 1;
                    startTime[2] = String.valueOf(tempDate);
                }
                startTime[3] = String.valueOf(temp);
                String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";
                dStatus3.setTime(tempTime);
                dStatus3.setAddress(addressB);
                result.put(status3, dStatus3);
            }

            int temp = Integer.valueOf(startTime[3]) + gap;
            startTime[3] = String.valueOf(temp);
            String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";
            dStatus4.setTime(tempTime);
            dStatus4.setAddress(receiverAddress);
            result.put(status4, dStatus4);
        }

        return result;
    }

    @Override
    public OrderTrackingRspDTO getTrackingInfo(Orders orders, String trackingNumebr) {

        OrderTrackingRspDTO orderTrackingRspDto = new OrderTrackingRspDTO();
        Deliverer curDeliverer = orders.getDeliverer();
        String type = curDeliverer.getType();
        List<Station> stations = stationRepository.findAll();


        String createTime = orders.getCreateTime();
        String[] time = createTime.substring(0, 13).split("-");
        String updateTime = "";
        String orderStatus = "";

        if (type == "drone") {
            int temp = Integer.valueOf(time[3]) + 12;
            time[3] = String.valueOf(temp);
            String arriveTime = time[0] + "-" + time[1] + "-" + time[2] + "-" + time[3] + ":" + "00";
            orderTrackingRspDto.setArriveTime(arriveTime);
            orderTrackingRspDto.setDelivererPath(getDelivererCurPath(stations, orders));
        } else {
            int temp = Integer.valueOf(time[3]) + 22 -24;
            int tempD = Integer.valueOf(time[2]) + 1;
            time[2] = String.valueOf(tempD);
            time[3] = String.valueOf(temp);
            String arriveTime = time[0] + "-" + time[1] + "-" + time[2] + "-" + time[3] + ":" + "00";
            orderTrackingRspDto.setArriveTime(arriveTime);
            orderTrackingRspDto.setDelivererPath(getDelivererCurPath(stations, orders));
        }
        Map<String, DeliverStatusDTO> delivererPath = new HashMap<>();
        delivererPath = orderTrackingRspDto.getDelivererPath();
        if (delivererPath.size() == 4) {
            updateTime = delivererPath.get("Stage4").getTime();
            orderStatus = "Delivered";
        } else if (delivererPath.size() == 3) {
            updateTime = delivererPath.get("Stage3").getTime();
            orderStatus = "Pending";
        } else if (delivererPath.size() == 2) {
            updateTime = delivererPath.get("Stage2").getTime();
            orderStatus = "Pending";
        } else {
            updateTime = delivererPath.get("Stage1").getTime();
            orderStatus = "Created";
        }

        orderTrackingRspDto.setTrackingNumber(trackingNumebr);
        orderTrackingRspDto.setCreateTime(createTime);
        orderTrackingRspDto.setDelivererPath(delivererPath);
        orderTrackingRspDto.setOrderStatus(orderStatus);
        orderTrackingRspDto.setUpdateTime(updateTime);
        return orderTrackingRspDto;
    }

}



