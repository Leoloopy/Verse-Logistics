package data.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private String orderId;
    private List<Item> item;
    private DeliveryStatus deliveryStatus;
    @NotBlank(message = "Add a receiver name")
    private String receiverName;
    private Address receiverAddress;
    @NotBlank
    private String receiverPhoneNumber;
    private LocalDateTime creationDate;
    private String senderId;

}
