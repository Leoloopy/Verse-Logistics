package data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestSignUp {
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    private String mobileNumber;

}
