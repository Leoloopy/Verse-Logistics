package services;

import com.verse.verselogistics.VerseLogisticsApplication;
import data.dtos.repositories.OrderRepository;
import data.dtos.repositories.SenderRepository;
import data.dtos.request.CreateSenderRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.LoginResponse;
import data.dtos.response.OrderResponse;
import data.dtos.response.SenderResponse;
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
class SenderServiceImplTest {

    @Autowired
    private UserService userService;
    private CreateSenderRequest senderRequest;
    @Autowired
    private SenderRepository senderRepository;

    @Autowired
    private OrderRepository orderRepository;
    private NewOrderRequest newOrderRequest;

    @BeforeEach
    void setUp() {

        Address senderAddress = Address.builder()
                .country("UK").state("London")
                .city("Manchester").street("10 Yorkshire street")
                .build();

         senderRequest = CreateSenderRequest.builder()
                .firstName("Michelson").lastName("Niklaus")
                .email("NikMic@mail.com").address(senderAddress)
                .password("thehybrid").phoneNumber("+4430021022")
                .build();

        // create a new user to be attached to order
        SenderResponse createNewUser = userService.registerSender(senderRequest);
        // create an order
        //address
        Address receiverAddress = Address.builder()
                .city("Paris")
                .country("France")
                .state("La ville Lumiere")
                .street("Rue de Rivoli")
                .build();
        //item
        List<Item> items = new ArrayList<>();
        Item item = Item.builder()
                .itemName("Hunters Blade").category(Category.NON_PERISHABLE)
                .build();

        Item item2 = Item.builder()
                .itemName("Piano").category(Category.NON_PERISHABLE)
                .build();

        items.add(item);items.add(item2);
        //build order request
        newOrderRequest = NewOrderRequest.builder()
                .receiverName("Elijah Michelson")
                .receiverAddress(receiverAddress)
                .receiverPhoneNumber("+33200577")
                .item(items)
                .userId(createNewUser.getId())
                .build();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        senderRepository.deleteAll();
    }

    @Test
    void testThatSenderCanBeRegistered() {
        SenderResponse firstSender = userService.registerSender(senderRequest);
        assertNotNull(userService.getSenderById(firstSender.getId()));
    }

    @Test
    void testThatSenderCanLogIn () {

        Address senderAddress = Address.builder()
                .country("UK").state("Lancashire")
                .city("Laceister").street("11 Davies boulevard")
                .build();

        senderRequest = CreateSenderRequest.builder()
                .firstName("Rebeca").lastName("Michelson")
                .email("rebmic@mail.com").address(senderAddress)
                .password("theOriginal").phoneNumber("+4420001010")
                .build();

        SenderResponse createNewUser = userService.registerSender(senderRequest);


        LoginRequest loginRequest = LoginRequest.builder()
                .email("rebmic@mail.com").password("theOriginal")
                .build();


        LoginResponse sendLogIn = userService.LogIntoDashboard(loginRequest);

        assertEquals(createNewUser.getId(), sendLogIn.getId());
        assertEquals(createNewUser.getEmail(), sendLogIn.getEmail());
        assertEquals(createNewUser.getFirstName(), sendLogIn.getFirstName());
    }

    @Test
    void testThatSenderCanMakeOrder() {
        OrderResponse firstOrderResponse =  userService.sendOrder(newOrderRequest);
        assertNotNull(orderRepository.findOrderByOrderId(firstOrderResponse.getOrderId()) );

    }

    @Test
    void testThatSenderCanDeleteOrder() {
        var firstSenderRequest = userService.sendOrder(newOrderRequest);
        userService.deleteOrderById(firstSenderRequest.getOrderId());
        assertNull(orderRepository.findOrderByOrderId(firstSenderRequest.getOrderId()));
    }

    @Test
    void checkDeliveryStatus() {
        var firstSenderRequest = userService.sendOrder(newOrderRequest);
        var orderStatus = userService.checkDeliveryStatus(firstSenderRequest.getOrderId());
        assertEquals(DeliveryStatus.PROCESSING , orderStatus);
    }

    @Test
    void testThatSenderOrderCanBeCancelled() {
        var firstSenderRequest = userService.sendOrder(newOrderRequest);
        userService.cancelOrderById(firstSenderRequest.getOrderId());
        assertEquals(DeliveryStatus.ON_HOLD, userService.checkDeliveryStatus(firstSenderRequest.getOrderId()));
    }

    @Test
    void testThatAllSendersOrderCanBeRetrieved(){
        var firstSenderRequest = userService.sendOrder(newOrderRequest);
        var secondSenderRequest = userService.sendOrder(newOrderRequest);
        var thirdSenderRequest = userService.sendOrder(newOrderRequest);

        List<Order> allOrdersBySender = userService.getAllSendersOrder(firstSenderRequest.getUserId());
        assertEquals(3, allOrdersBySender.size());
    }
}