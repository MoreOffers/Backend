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

    @Column
    private Long delivererId;

    @Column
    private String stationAddress;
}
