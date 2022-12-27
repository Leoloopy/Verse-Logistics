package data.dtos.request;

import data.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSenderRequest {
    @NotBlank(message = "firstName cannot be empty")
    private String firstName;
    @NotBlank(message = "lastName cannot be empty")
    private String lastName;
    @NotBlank(message = "email cannot be empty")
    @Email()
    private String email;
    @NotBlank(message = "provide a password")
    private String password;
    @NotBlank(message = "field cannot be empty")
    private String phoneNumber;
    private LocalDateTime creationDate;
    @NotBlank(message = "field cannot be empty")
    private Address address;

}
