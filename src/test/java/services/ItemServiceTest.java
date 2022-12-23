package services;

import com.verse.verselogistics.VerseLogisticsApplication;
import data.dtos.request.AddNewItemRequest;
import data.models.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = VerseLogisticsApplication.class)
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    private AddNewItemRequest addNewItemRequest;

    @BeforeEach
    void setUp() {
       addNewItemRequest = new AddNewItemRequest("cloth", Category.NON_FRAGILE);
    }

    @AfterEach
    void tearDown() {
        itemService.deleteAllItems();
    }

    @Test
    void testThatItemCanBeAdded(){
        var savedItems = itemService.addItem(addNewItemRequest);
        assertEquals(savedItems, itemService.getItemById(savedItems.getId()));
    }

    @Test
    void testThatItemCanBeRemoved(){
        var newItemRequest = new AddNewItemRequest("bag", Category.NON_FRAGILE);
        var savedItems = itemService.addItem(newItemRequest);
        itemService.removeItem(savedItems);
        assertNull(itemService.getItemByName(savedItems.getName()));
    }
}