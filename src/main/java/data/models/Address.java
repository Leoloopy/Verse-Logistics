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
@Document
@Builder
public class Address {
    @Id
    private String Id;
    @NotNull(message = "field cannot be empty")
    private String city;
    private String country;
    @NotNull(message = "field cannot be empty")
    private String state;
    @NotNull(message = "field cannot be empty")
    private String street;

}
