package data.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    @Id
    private String id;
    @NotNull(message = "field can't be null")
    private String firstName;
    @NotNull(message = "field can't be null")
    private String lastName;
    @Email(message = "invalid email address")
    private String email;
    @DBRef
    private Address address;
    private Item item;
    private Location location;
    @NotNull(message = "password field cannot be empty")
    private String password;

}
