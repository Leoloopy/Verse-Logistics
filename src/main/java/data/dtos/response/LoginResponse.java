package data.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String firstName;
    private String Id;
    private String email;
}
