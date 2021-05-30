package AIDeliver.com.example.AIDeliver.dto;

import lombok.Data;


import java.util.Date;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String Address;
    private String zipCode;
    private String mobile;
    private double credit;
    private Date birthday;

}
