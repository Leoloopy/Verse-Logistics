package services;

import com.verse.verselogistics.VerseLogisticsApplication;
import data.dtos.request.NewOrderRequest;
import data.models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VerseLogisticsApplication.class)
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    private NewOrderRequest newOrderRequest;

    @BeforeEach
    void setUp() {

        Address receiverAddress = Address.builder()
                .city("Paris")
                .country("France")
                .state("La ville Lumiere")
                .street("Rue de Rivoli")
                .build();

        List<Item> items = new ArrayList<>();
     Item item = Item.builder()
                     .itemName("Guitar")
                     .category(Category.NON_PERISHABLE)
             .build();

        Item item2 = Item.builder()
                .itemName("Piano")
                .category(Category.NON_PERISHABLE)
                .build();

        Item item3 = Item.builder()
                .itemName("Violin")
                .category(Category.NON_PERISHABLE)
                .build();

     items.add(item);
     items.add(item2);
     items.add(item3);



        newOrderRequest = NewOrderRequest.builder()
                .receiverName("Bukola Elemide")
                .receiverAddress(receiverAddress)
                .receiverPhoneNumber("+33200577")
                .item(items)
                .build();
    }

    @AfterEach
    void tearDown() {
        orderService.deleteAllOrders();
    }

    @Test
    void testThatOrdersCanBeSaved() {
        Order savedOrder = orderService.saveOrder(newOrderRequest);
        assertNotNull(orderService.getOrderById(savedOrder.getOrderId()));
        assertEquals(savedOrder, orderService.getOrderById(savedOrder.getOrderId()));
    }


    @Test
    void testThatOrdersCanBeDeletedById() {
        Order savedOrder = orderService.saveOrder(newOrderRequest);
        orderService.deleteOrderById(savedOrder.getOrderId());
        assertNull(orderService.getOrderById(savedOrder.getOrderId()));
    }

    /*
    @Test
    void testThatAllOrdersCanBeViewed() {
        Order savedOrder1 = orderService.saveOrder(newOrderRequest, receiverAddress, items );
        Order savedOrder2 = orderService.saveOrder(newOrderRequest, receiverAddress, items );
        Order savedOrder3 = orderService.saveOrder(newOrderRequest, receiverAddress, items );

        List<Order> allOrders = orderService.getAllOrders(savedOrder1.getSenderId());
        assertEquals(3, allOrders.size());
    }
     */

    @Test
    void testThatWeCanCheckDeliveryStatusOfAnOrder() {
        Order savedOrder = orderService.saveOrder(newOrderRequest);
        assertEquals(DeliveryStatus.PROCESSING , orderService.checkDeliveryStatus(savedOrder.getOrderId()));
    }

    @Test
    void testThatOrderCanBeCancelled(){
        Order savedOrder = orderService.saveOrder(newOrderRequest);
        orderService.cancelOrder((savedOrder.getOrderId()));
        assertEquals(DeliveryStatus.ON_HOLD, orderService.checkDeliveryStatus(savedOrder.getOrderId()));
    }

    /*
    @Test
    void updateOrder() {
        Order savedOrder = orderService.saveOrder(newOrderRequest, receiverAddress, items );
    }
     */
}