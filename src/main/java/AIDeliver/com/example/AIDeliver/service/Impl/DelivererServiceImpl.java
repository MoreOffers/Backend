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


    private String tracking(List<Station> stations, int zipcode) {
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



    private Map<String, DeliverStatusDTO> getDelivererCurPath(List<Station> stations, Orders orders, String type) {
        int[] gapArray; int gap;

        if (type == Constant.DRONE) {
            gapArray = new int[]{3, 6, 9, 12};
            gap = 3;
        } else {
            gapArray = new int[]{5, 10, 15, 20};
            gap = 5;
        }

        int senderZip = Integer.valueOf(orders.getSenderZipCode()), receiverZip = Integer.valueOf(orders.getReceiverZipCode());
        String senderAddress = orders.getSenderAddress(), receiverAddress = orders.getReceiverAddress();

        String start = orders.getCreateTime();
        String time = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(Calendar.getInstance().getTime());
        String end = time.substring(0, 16);

        String tempTime;
        String startsub = start.substring(0, 13), endsub = end.substring(0, 13);
        String[] startTime = startsub.split("-");
        int startH = Integer.valueOf(startTime[3]), endH = Integer.valueOf(endsub.split("-")[3]);
        int startD = Integer.valueOf(startTime[2]), endD = Integer.valueOf(endsub.split("-")[2]);

        Map<String, DeliverStatusDTO> result = new HashMap<>();
        String[] stages = new String[] {"Stage1", "Stage2", "Stage3", "Stage4"};

        DeliverStatusDTO dStatus1 = new DeliverStatusDTO();
        DeliverStatusDTO dStatus2 = new DeliverStatusDTO();
        DeliverStatusDTO dStatus3 = new DeliverStatusDTO();
        DeliverStatusDTO dStatus4 = new DeliverStatusDTO();

        String addressA = tracking(stations, senderZip), addressB = tracking(stations, receiverZip);

        endH = endD != startD ? endH + 24: endH;

        if (endH - startH < gapArray[0]) {
            updateMap(result, senderAddress, end, dStatus1, stages[0]);
        } else if (endH - startH < gapArray[1]) {
            checkMap(result, startTime, gap, dStatus1, stages[0], senderAddress);
            updateMap(result, addressA, end, dStatus2, stages[1]);
        } else if (endH - startH < gapArray[2]) {
            checkMap(result, startTime, gap, dStatus1, stages[0], senderAddress);
            checkMap(result, startTime, gap, dStatus2, stages[1], addressA);
            updateMap(result, addressB, end, dStatus3, stages[2]);
        } else {
            checkMap( result, startTime, gap, dStatus1, stages[0], senderAddress);
            checkMap( result, startTime, gap, dStatus2, stages[1], addressA);
            checkMap( result, startTime, gap, dStatus3, stages[2], addressB);

            if (endH - startH < gapArray[3]) {
                updateMap(result, receiverAddress, end, dStatus4, stages[3]);
            } else {
                tempTime = updateTime(startTime, gap);
                updateMap(result, receiverAddress, tempTime, dStatus4, stages[3]);
            }
        }

        return result;
    }

    private void updateMap(Map<String, DeliverStatusDTO> result, String address, String time, DeliverStatusDTO dStatusDTO, String stage) {
        dStatusDTO.setTime(time);
        dStatusDTO.setAddress(address);
        result.put(stage, dStatusDTO);
    }

    private String updateTime(String[] startTime, int gap) {
        int temp = Integer.valueOf(startTime[3]) + gap;
        if (temp >= 24) {
            temp -= 24;
            int tempDate = Integer.valueOf(startTime[2]) + 1;
            startTime[2] = String.valueOf(tempDate);
        }
        startTime[3] = String.valueOf(temp);
        String tempTime = startTime[0] + "-" + startTime[1] + "-" + startTime[2] + "-" + startTime[3] + ":" + "00";

        return tempTime;
    }

    private void checkMap(Map<String, DeliverStatusDTO> result, String[] startTime, int gap, DeliverStatusDTO dStatusDTO, String stage, String address) {
        if (result.containsKey(stage)) {
            result.remove(stage);
        } else {
            String tempTime = updateTime(startTime, gap);
            dStatusDTO.setTime(tempTime);
            dStatusDTO.setAddress(address);
            result.put(stage, dStatusDTO);
        }
    }

    @Override
    public OrderTrackingRspDTO getTrackingInfo(Orders orders, String trackingNumebr) {
        OrderTrackingRspDTO orderTrackingRspDTO = new OrderTrackingRspDTO();
        String type = orders.getDeliverer().getType();
        List<Station> stations = stationRepository.findAll();

        String[] time = orders.getCreateTime().substring(0, 13).split("-");
        String updateTime = "", orderStatus = "";

        orderTrackingRspDTO = getArriveTime(orderTrackingRspDTO, type, time);
        Map<String, DeliverStatusDTO> delivererPath = getDelivererCurPath(stations, orders, type);

        int size = delivererPath.size();
        int[] sizeArray = new int[] {1, 2, 3, 4};

        for (int i = 3; i >= 0; i--) {
            if (size == sizeArray[i] && i <=2) {
                updateTime = delivererPath.get("Stage" + String.valueOf(sizeArray[i])).getTime();
                if (i == 0) {
                    orderStatus = Constant.CREATED_STATUS;
                } else {
                    orderStatus = Constant.PENDING_STATUS;
                }
            } else if (size == sizeArray[i]) {
                updateTime = delivererPath.get("Stage" + String.valueOf(sizeArray[i])).getTime();
                orderStatus = Constant.COMPLETED_STATUS;
            }
        }

        orderTrackingRspDTO.setTrackingNumber(trackingNumebr);
        orderTrackingRspDTO.setCreateTime(orders.getCreateTime());
        orderTrackingRspDTO.setDelivererPath(delivererPath);
        orderTrackingRspDTO.setOrderStatus(orderStatus);
        orderTrackingRspDTO.setUpdateTime(updateTime);
        return orderTrackingRspDTO;
    }

    private OrderTrackingRspDTO getArriveTime(OrderTrackingRspDTO orderTrackingRspDTO, String type, String[] time) {
        String arriveTime;
        int temp;

        if (type == Constant.DRONE) {
            temp = Integer.valueOf(time[3]) + 12;
        } else {
            temp = Integer.valueOf(time[3]) + 20 -24;
            int tempD = Integer.valueOf(time[2]) + 1;
            time[2] = String.valueOf(tempD);
        }

        time[3] = String.valueOf(temp);
        arriveTime = time[0] + "-" + time[1] + "-" + time[2] + "-" + time[3] + ":" + "00";
        orderTrackingRspDTO.setArriveTime(arriveTime);
        return orderTrackingRspDTO;
    }




}



