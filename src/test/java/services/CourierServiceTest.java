package services;

import com.verse.verselogistics.VerseLogisticsApplication;
import data.dtos.repositories.CourierRepository;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.request.NewUserRequest;
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
class CourierServiceTest {

    @Autowired
    @Qualifier("courier")
    private UserService userService;
    private NewUserRequest newCourierRequest;
    private NewOrderRequest orderRequest;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        Address courierAddress = Address.builder()
                .country("Kenya")
                .state("Harare")
                .city("Nairobi")
                .street("10 Kenyatta street")
                .build();

        newCourierRequest = NewUserRequest.builder()
                .firstName("William")
                .lastName("Ruto")
                .email("williamruto@gmail.com")
                .password("blackhead")
                .phoneNumber("+11909011")
                .address(courierAddress)
                .build();

        // Build Orders

        Address deliveryAddress = Address.builder()
                .city("ikeja").country("Nigeria")
                .state("Lagos").street("10 owolabi street")
                .build();

        Item item_one = Item.builder()
                .itemName("Fruits")
                .category(Category.PERISHABLE)
                .build();
        Item item_two = Item.builder()
                .itemName("Phone")
                .category(Category.NON_FRAGILE)
                .build();

        List<Item> newItemsList  = new ArrayList<>();
        newItemsList.add(item_one);
        newItemsList.add(item_two);

         orderRequest = NewOrderRequest.builder()
                .receiverName("Mr Anjola")
                .receiverAddress(deliveryAddress)
                .receiverPhoneNumber("00872727")
                .item(newItemsList)
                .build();
    }

    @AfterEach
    void tearDown() {
        courierRepository.deleteAll();
    }

    @Test
    void testThatACourierCanRegister() {
     NewUserResponse response = userService.registerUser(newCourierRequest);
     assertNotNull(userService.getUserById(response.getId()));
    }

    @Test
    void testThatCourierCanRegisterWithOneEmail(){
        Address courierAddress = Address.builder()
                .street("10 Ajegunle street")
                .city("Ajegunle")
                .state("Lagos")
                .country("Nigeria")
                .build();

        NewUserRequest userRequest = NewUserRequest.builder()
                .address(courierAddress)
                .firstName("Pa James")
                .lastName("Ajirebi")
                .email("jamesAjirebi@gmail.com")
                .phoneNumber("+23455667788")
                .password("ajirebi001")
                .build();

        NewUserResponse userResponse = userService.registerUser(userRequest);
        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(userRequest));
    }

    @Test
    void logIntoDashboard() {
        Address courierAddress = Address.builder()
                .city("ikeja").country("Nigeria")
                .state("Lagos").street("10 owolabi street")
                .build();

        NewUserRequest userRequest = NewUserRequest.builder()
                .firstName("Mataya").lastName("Mendes")
                .phoneNumber("+23490874511").email("mendesMataya@gmail")
                .password("mendesmaya").address(courierAddress)
                .build();

        NewUserResponse registrationResponse = userService.registerUser(userRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .password("mendesmaya").email("mendesMataya@gmail")
                .build();

        LoginResponse newCourierResponse = userService.LogIntoDashboard(loginRequest);

        assertEquals(newCourierResponse.getId(), registrationResponse.getId());
        assertEquals(newCourierResponse.getEmail(), registrationResponse.getEmail());
        assertEquals(newCourierResponse.getFirstName(), registrationResponse.getFirstName());
    }

    @Test
    void testThatCourierCanGetAllOrdersInDB() {
        Order firstOrder = orderService.saveOrder(orderRequest);
        Order secondOrder = orderService.saveOrder(orderRequest);
        Order thirdOrder = orderService.saveOrder(orderRequest);

        List<Order> allOrders = userService.getAllOrders();
        assertEquals(3, allOrders.size());

    }

    @Test
    void deleteOrderById() {
        Order firstOrder = orderService.saveOrder(orderRequest);
        assertEquals(firstOrder, userService.getOrderById(firstOrder.getOrderId()));

    }

    @Test
    void cancelOrderById() {
        Order savedOrder = orderService.saveOrder(orderRequest);
        userService.cancelOrderById(savedOrder.getOrderId());
        assertEquals(DeliveryStatus.ON_HOLD, userService.checkDeliveryStatus(savedOrder.getOrderId()));
    }

}