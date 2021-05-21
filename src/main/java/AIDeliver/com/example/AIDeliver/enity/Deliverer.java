package AIDeliver.com.example.AIDeliver.enity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Deliverer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String type;

    @Column(name = "is_availability", nullable = false)
    private boolean isAvailability;

    @Column(name = "max_weight", nullable = false)
    private int maxWeight;

    @Column
    private double price;

    @Column(name = "current_position")
    private String currentPosition;

}
