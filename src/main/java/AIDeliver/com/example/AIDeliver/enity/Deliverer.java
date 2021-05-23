package AIDeliver.com.example.AIDeliver.enity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Deliverer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "type", nullable = false)
    private String type;

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
    @ManyToOne
    @Column
    private Station station;

    /*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
     */

    public String getCurPosition() {

        return current_position;
    }

    public boolean getEquipStatus() {

        return equip_status;
    }

    public int getMaxWeight() {
        return max_weight;
    }

    public void setEquipId(String equip_id) {
        this.equip_id = equip_id;
    }

    public String getEquipId(String equip_id) {
        return equip_id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }


}