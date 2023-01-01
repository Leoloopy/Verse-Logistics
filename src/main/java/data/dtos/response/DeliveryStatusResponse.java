package data.dtos.response;

import data.models.DeliveryStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryStatusResponse {
    private DeliveryStatus deliveryStatus;
}
