package data.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @NotNull(message = "first name can't be empty")
    private String firstName;
    @NotNull(message = "last name can't be empty")
    private String lastName;
    @Email(message = "invalid email address")
    private String email;
    @DBRef
    private Address address;
    @NotNull(message = "password field cannot be empty")
    private String password;
    @NotNull(message = "")
    private String phoneNumber;

}
