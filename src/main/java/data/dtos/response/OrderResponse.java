package data.dtos.response;

import data.models.DeliveryStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private String userId;
    private String orderId;
    private LocalDateTime orderDate;
    private String receiverName;
    private DeliveryStatus deliveryStatus;
}
