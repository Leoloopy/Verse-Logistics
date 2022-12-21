package services;

import data.dtos.repositories.ItemRepository;
import data.dtos.request.AddNewItemRequest;
import data.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository ;

    @Override
    public Item addItem(AddNewItemRequest addNewItemRequest) {
        var unWrapItems = new Item(addNewItemRequest.getItem(), addNewItemRequest.getCategory());
        return itemRepository.save(unWrapItems);
    }

    @Override
    public void deleteAllItems() {
        itemRepository.deleteAll();
    }
}
