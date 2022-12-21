package data.dtos.request;

import data.models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewItemRequest {
    private String id;
    private String item;
    private Category category;

    public AddNewItemRequest(String item, Category category) {
        this.item = item;
        this.category = category;
    }
}
