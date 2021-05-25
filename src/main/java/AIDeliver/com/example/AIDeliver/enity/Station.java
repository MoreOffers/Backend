package AIDeliver.com.example.AIDeliver.enity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="STATION")
@Data
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //add zipCode
    @Column(name = "zipcode", nullable = false)
    private Integer zipcode;

    @Column(name = "station_address", nullable = false)
    private String station_address;

    @OneToMany(targetEntity = Deliverer.class, cascade = CascadeType.ALL)
    @JoinColumn(name ="station_id",referencedColumnName = "id", nullable = true)
    private List<Deliverer> deliverers;
}
