package data.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String userId;
    @NotNull(message = "first name can't be empty")
    private String firstName;
    @NotNull(message = "last name can't be empty")
    private String lastName;
    @Email(message = "invalid email address")
    private String email;
    private Set<Address> address;
    @NotNull(message = "password field cannot be empty")
    private String password;
    @NotNull(message = "")
    private String phoneNumber;
    private LocalDateTime creationDate;

}
