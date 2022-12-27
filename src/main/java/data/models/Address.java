package data.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @NotNull(message = "field cannot be empty")
    private String city;
    private String country;
    @NotNull(message = "field cannot be empty")
    private String state;
    @NotNull(message = "field cannot be empty")
    private String street;

}
