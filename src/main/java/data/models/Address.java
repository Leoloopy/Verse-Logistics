package data.models;

import jakarta.validation.constraints.NotNull;

public class Address {
    @NotNull(message = "field cannot be empty")
    private String city;
    private String country;
    @NotNull(message = "field cannot be empty")
    private String state;
    @NotNull(message = "field cannot be empty")
    private String street;

}
