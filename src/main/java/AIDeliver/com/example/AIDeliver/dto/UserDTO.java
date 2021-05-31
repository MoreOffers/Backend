package AIDeliver.com.example.AIDeliver.dto;

import lombok.Data;


import javax.persistence.Id;
import java.util.Date;

@Data
public class UserDTO {

    @Id
    private Long id;
    private String name;
    private String password;
    private String email;
    private String Address;
    private String zipCode;
    private String mobile;
    private double credit;
    private Date birthday;
    private Boolean isVisitor;

}
