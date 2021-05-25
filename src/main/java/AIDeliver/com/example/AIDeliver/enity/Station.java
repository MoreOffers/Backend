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

    //add zipCode
    @Column(name = "zipcode", nullable = false)
    private int zipcode;

    @Column(name = "station_address", nullable = false)
    private String station_address;

    public int getZipcode() {
        return zipcode;
    }

    public String getStationAddress() {
        return station_address;
    }

}
