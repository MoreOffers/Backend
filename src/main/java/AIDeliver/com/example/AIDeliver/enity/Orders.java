package AIDeliver.com.example.AIDeliver.enity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ORDERS")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String trackingNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

    @OneToOne(targetEntity = Deliverer.class, cascade = CascadeType.ALL)
    @JoinColumn(name="deliverer_id", referencedColumnName = "id", nullable=true)
    private Deliverer deliverer;

    @Column(name = "sender_address")
    private String senderAddress;

    @Column(name = "sender_mobile", nullable = true)
    private String senderMobile;

    @Column(name = "sender_name", nullable = true)
    private String senderName;

    @Column(name = "sender_zipCode", nullable = true)
    private String senderZipCode;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_mobile", nullable = true)
    private String receiverMobile;

    @Column(name = "receiver_name", nullable = true)
    private String receiverName;

    @Column(name = "receiver_zipCode", nullable = true)
    private String receiverZipCode;

    @Column(name = "order_status", nullable = true)
    private String orderStatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = true)
    private Date createTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = true)
    private Date updateTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrive_time", nullable = true)
    private Date arriveTime;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @Column(name = "payment_card", nullable = true)
    private String paymentCard;

    @Column(name = "weight")
    private String weight;

    @Column
    private String size;

}