package data.dtos.request;

import data.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewUserRequest {
    private String firstName;
    private String lastName;
    private String Email;
    private String password;
    private String mobileNumber;
    private Address address;
}
