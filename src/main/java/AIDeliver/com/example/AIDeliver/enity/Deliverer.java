package AIDeliver.com.example.AIDeliver.enity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="DELIVERER")
@Data
public class Deliverer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "equip_status", nullable = true)
    private Boolean equip_status;

    @Column(name = "max_weight", nullable = true)
    private Integer max_weight;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "current_position", nullable = true)
    private String current_position;

    @Column(name = "equip_number", nullable = true)
    private String equip_id;

    @Column(name = "is_available", nullable = true)
    private Boolean is_available;

}