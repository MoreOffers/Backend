package AIDeliver.com.example.AIDeliver.enity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="DELIVERER")
@Data
public class Deliverer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(columnDefinition = "long default 1")
    private long id;

    @Column(name = "type", nullable = false)
    private String type;

//    @OneToOne
//    @JoinColumn(name="orders_id", referencedColumnName = "id", nullable=true)
//    private Orders orders;

    @Column
    private boolean equip_status;

    @Column
    private int max_weight;

    @Column(name = "price", nullable = false)
    private double price;

    @Column
    private String current_position;

    @Column
    private String equip_id;

    //add station
//    @ManyToOne
//    private Station station;

    /*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
     */



}