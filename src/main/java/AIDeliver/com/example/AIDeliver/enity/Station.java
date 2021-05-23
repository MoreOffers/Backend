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


    /* test
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    String address;
    int zipCode;

    public Station(String address, int zipCode) {
        this.address = address;
        this.zipCode = zipCode;
    }
     */


}
