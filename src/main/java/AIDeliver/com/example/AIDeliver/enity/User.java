package AIDeliver.com.example.AIDeliver.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", nullable = true)
    private String name;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(nullable = true)
    private String Address;

    @Column(nullable = true)
    private String zipCode;

    @Column(nullable = true)
    private String mobile;

    @Column
    private double credit = 100.00;

    @Column(nullable = true)
    private Date birthday;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(targetEntity = Orders.class, cascade = CascadeType.ALL)
    @JoinColumn(name ="user_id",referencedColumnName = "id", nullable = true)
    private List<Orders> orders;

    @Column(name = "is_visitor", nullable = true)
    private Boolean isVisitor=false;

}
