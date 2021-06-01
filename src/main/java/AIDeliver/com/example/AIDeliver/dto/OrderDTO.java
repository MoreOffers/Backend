package AIDeliver.com.example.AIDeliver.dto;

import AIDeliver.com.example.AIDeliver.enity.Deliverer;
import AIDeliver.com.example.AIDeliver.enity.User;
import lombok.Data;

import javax.persistence.Id;

@Data
public class OrderDTO {

    private String trackingNumber;
    private User user;
    private Deliverer deliverer;
    private String senderAddress;
    private String senderMobile;
    private String senderName;
    private String senderZipCode;
    private String receiverAddress;
    private String receiverMobile;
    private String receiverName;
    private String receiverZipCode;
    private String orderStatus;
    private double paymentAmount;
    private String weight;
    private String size;
    private String status;
    private String createTime;
}
