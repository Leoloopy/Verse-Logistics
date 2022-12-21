package data.models;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Address {
    @Id
    private String id;
    @NotNull(message = "field cannot be empty")
    private String city;
    private String country;
    @NotNull(message = "field cannot be empty")
    private String state;
    @NotNull(message = "field cannot be empty")
    private String street;

}
