package data.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private String id;
    @NotNull(message = "name cannot be empty")
    private String name;
    private LocalDateTime localDateTime  = LocalDateTime.now();
    @NotNull(message = "Please indicate the item Category")
    private Category category;
    private DeliveryStatus deliveryStatus = DeliveryStatus.PROCESSING;
    public Item(String name, Category category){
        this.name = name;
        this.category = category;
    }
}
