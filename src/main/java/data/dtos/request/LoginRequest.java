package data.dtos.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LoginRequest {
    private String password;
    private String email;
}
