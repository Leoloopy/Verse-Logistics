package services;

import data.dtos.request.AddNewItemRequest;
import data.models.Item;
import org.springframework.stereotype.Service;

public interface ItemService {
    Item addItem(AddNewItemRequest addNewItemRequest);
    void deleteAllItems();
    void removeItem(Item savedItems);
    Item getItemByName(String s);
    Item getItemById(String s);
}
