package AIDeliver.com.example.AIDeliver.enity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "deliverer_id", nullable = false)
    private Long delivererId;

    @Column(name = "station_address", nullable = false)
    private String stationAddress;
}
