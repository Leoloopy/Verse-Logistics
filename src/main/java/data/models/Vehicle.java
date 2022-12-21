package data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Vehicle {
    @Id
    private String id;
    private String plateNumber;
    private Boolean vehicleStatus;
}
