package data.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
public class Item {
    @NotBlank(message = "name cannot be empty")
    private String itemName;
    @NotBlank(message = "Please indicate the item Category")
    private Category category;
}
