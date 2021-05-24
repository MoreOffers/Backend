package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.SalesOrder;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.SalesOrderRepository;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.SalesOrderService;
import AIDeliver.com.example.AIDeliver.tools.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final UserRepository userRepository;


    @Autowired
    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository, UserRepository userRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.userRepository = userRepository;

    }

    public List<SalesOrder> getHistorySalesOrders(Long id) {
        return salesOrderRepository.findSalesOrderByUserId(id);
    }

    public Boolean createSalesOrder(SalesOrder salesOrder, DeliveryOption deliveryOption, User user) {
        //order.delivererId = delivererId
        Optional<SalesOrder> salesOrderOptional = salesOrderRepository.findSalesOrderById(salesOrder.getId());
        if (salesOrderOptional.isPresent()) {
            throw new IllegalStateException("order already existed");
        }
        salesOrder.setPaymentAmount(deliveryOption.priceOption);
        salesOrder.setUser(user);
        salesOrder.setOrderStatus(OrderStatus.InProgress.toString());

//        salesOrder.setSalesOrderStatus("order created");
//        salesOrder.setDelivererId(deliverer.getId());
        salesOrderRepository.save(salesOrder);
        return true;
        }

    public Optional<SalesOrder> salesOrderDetail(Long salesOrderId) {
        return salesOrderRepository.findSalesOrderById(salesOrderId);
    }

    public String getSalesOrderStatus(Long salesOrderId) {
        Optional<SalesOrder> orderOptional = salesOrderRepository.findSalesOrderById(salesOrderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalStateException("order does not exist");
        }
        return salesOrderRepository.findSalesOrderStatusById(salesOrderId);
    }

    public void updateSalesOrderStatus(Long salesOrderId, String status) {

            SalesOrder existingSalesOrder = salesOrderRepository.findById(salesOrderId)
                    .orElseThrow(() -> new IllegalStateException(
                            "order with id " + salesOrderId + " does not exist."
                    ));
            existingSalesOrder.setOrderStatus(status);
            salesOrderRepository.save(existingSalesOrder);
        }

    public List<DeliveryOption> calculatePrice(SalesOrder salesOrder) {
        //sender zipcode-receiver zip code
        String key = salesOrder.getSenderZipCode() + salesOrder.getReceiverZipCode();

        ZipCode zipCode = new ZipCode();
        Coordinate coordinate = new Coordinate(salesOrder.getSenderZipCode(), salesOrder.getReceiverZipCode());

        double paymentAmount1 = salesOrder.getWeight() * zipCode.calTable.get(coordinate) * 20; // 20 needs to be updated drone
        double paymentAmount2 = salesOrder.getWeight() * zipCode.calTable.get(coordinate) * 10; // 10 needs to be updated robot

        List<DeliveryOption> list = new ArrayList<>();
        DeliveryOption drone = new DeliveryOption();
        drone.priceOption = paymentAmount1;
        drone.type = "drone";

        DeliveryOption robot = new DeliveryOption();
        robot.priceOption = paymentAmount2;
        robot.type = "robot";

        list.add(drone);
        list.add(robot);

        return list;
    }
    }
