package data.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private String id;
    @NotNull(message = "name cannot be empty")
    private String name;
    private LocalDateTime creationDate = this.setLocalDateTime();
    @NotNull(message = "Please indicate the item Category")
    private Category category;
    private DeliveryStatus deliveryStatus = DeliveryStatus.PROCESSING;
    public Item(String name, Category category){
        this.name = name;
        this.category = category;
    }

    private LocalDateTime setLocalDateTime() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String text = date.format(formatter);
        return LocalDateTime.parse(text, formatter);
    }

}
