package AIDeliver.com.example.AIDeliver.enity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String trackingNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "deliverer_id", nullable = false)
    private Long delivererId;

    @Column(name = "sender_address")
    private String senderAddress;

    @Column(name = "sender_mobile")
    private String senderMobile;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "sender_zipCode")
    private String senderZipCode;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_mobile")
    private String receiverMobile;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_zipCode")
    private String receiverZipCode;

    @Column(name = "order_status")
    private String orderStatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrive_time", nullable = false)
    private Date arriveTime;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @Column(name = "payment_card")
    private String paymentCard;

    @Column(name = "weight")
    private Double weight;

}