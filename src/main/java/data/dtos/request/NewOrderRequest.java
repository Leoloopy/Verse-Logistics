package data.dtos.request;

import data.models.Address;
import data.models.Category;
import data.models.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewOrderRequest {
    private String userId;
    private String receiverName;
    private Address receiverAddress;
    private Category category;
    private String receiverPhoneNumber;
    private List<Item> item;
}
