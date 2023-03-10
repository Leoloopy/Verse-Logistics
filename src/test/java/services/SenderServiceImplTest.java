package services;

import com.verse.verselogistics.VerseLogisticsApplication;
import data.dtos.repositories.OrderRepository;
import data.dtos.repositories.SenderRepository;
import data.dtos.request.NewUserRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.LoginResponse;
import data.dtos.response.NewOrderResponse;
import data.dtos.response.NewUserResponse;
import data.models.*;
import exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VerseLogisticsApplication.class)
class SenderServiceImplTest {


    @Autowired
    @Qualifier("sender")
    private UserService userService;
    private NewUserRequest senderRequest;
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

         senderRequest = NewUserRequest.builder()
                .firstName("Michelson").lastName("Niklaus")
                .email("NikMic@mail.com").address(senderAddress)
                .password("thehybrid").phoneNumber("+4430021022")
                .build();

        // create a new user to be attached to order
        NewUserResponse createNewUser = userService.registerUser(senderRequest);
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
        Address senderAddress = Address.builder()
                .country("Australia").state("Sydney")
                .city("Melbourne").street("12 Gabby street")
                .build();

        NewUserRequest registerSender = NewUserRequest.builder()
                .firstName("Michelson").lastName("kol")
                .email("kolMic@mail.com").address(senderAddress)
                .password("pureVamp").phoneNumber("+2001001")
                .build();
        NewUserResponse reg = userService.registerUser(registerSender);
        assertNotNull(userService.getUserById(reg.getId()));
    }

    @Test
    void testThatSenderCanLogIn () {

        Address senderAddress = Address.builder()
                .country("UK").state("Lancashire")
                .city("Laceister").street("11 Davies boulevard")
                .build();

        senderRequest = NewUserRequest.builder()
                .firstName("Rebeca").lastName("Michelson")
                .email("rebmic@mail.com").address(senderAddress)
                .password("theOriginal").phoneNumber("+4420001010")
                .build();

        NewUserResponse createNewUser = userService.registerUser(senderRequest);


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
        NewOrderResponse firstOrderResponse =  userService.sendOrder(newOrderRequest);
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

    @Test
    void testThatUsersCanOnlyRegisterWithOneEmail(){
        Address newUserAddress = Address.builder()
                .street("10 iwaya road").city("yaba")
                .state("lagos").country("nigeria")
                .build();

        NewUserRequest newSenderDetails = NewUserRequest.builder()
                .firstName("James").lastName("Aduloju")
                .phoneNumber("+2349010010").password("jamierachie")
                .email("jamesAduloju@gmail.com").address(newUserAddress)
                .build();

        NewUserResponse saveJamesInfoToDB = userService.registerUser(newSenderDetails);
        assertNotNull(userService.getUserById(saveJamesInfoToDB.getId()));
      assertThrows(UserAlreadyExistException.class,()->{ userService.registerUser(newSenderDetails);});
    }

}