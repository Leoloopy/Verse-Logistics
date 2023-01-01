package data.dtos.request;

import data.models.DeliveryStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryStatusRequest {
    private String id;
    private DeliveryStatus deliveryStatus;
}
