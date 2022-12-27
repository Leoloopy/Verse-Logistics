package data.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SenderResponse {
    private String firstName;
    private String id;
    private String email;
}
