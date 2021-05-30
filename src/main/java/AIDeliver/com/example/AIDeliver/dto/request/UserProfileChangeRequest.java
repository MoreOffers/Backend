package AIDeliver.com.example.AIDeliver.dto.request;

import lombok.Data;

@Data
public class UserProfileChangeRequest {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String zipCode;
    private String mobile;
    private Double credit;
}
