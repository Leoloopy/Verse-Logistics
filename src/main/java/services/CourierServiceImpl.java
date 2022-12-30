package services;

import data.dtos.repositories.CourierRepository;
import data.dtos.request.NewUserRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.LoginResponse;
import data.dtos.response.NewOrderResponse;
import data.dtos.response.NewUserResponse;
import data.models.*;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Qualifier("courier")
public class CourierServiceImpl implements UserService{

    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private OrderService orderService;

    @Override
    public NewUserResponse registerUser(NewUserRequest courierRequest) {
        if (checkDBForExistingAccount(courierRequest))
            throw new UserAlreadyExistException("courier with email " + courierRequest.getEmail() + " already exist");
        HashSet<Address> addresses = new HashSet<>();
        Address courierAddress = Address.builder()
                .street(courierRequest.getAddress().getStreet())
                .city(courierRequest.getAddress().getCity())
                .state(courierRequest.getAddress().getState())
                .country(courierRequest.getAddress().getCountry())
                .build();
        addresses.add(courierAddress);

        Courier newCourier = new Courier();
        newCourier.setCreationDate(LocalDateTime.now());
        newCourier.setEmail(courierRequest.getEmail());
        newCourier.setFirstName(courierRequest.getFirstName());
        newCourier.setLastName(courierRequest.getLastName());
        newCourier.setPassword(courierRequest.getPassword());
        newCourier.setPhoneNumber(courierRequest.getPhoneNumber());
        newCourier.setAddress(addresses);

        Courier registerCourier = courierRepository.save(newCourier);
        return NewUserResponse.builder()
                .email(registerCourier.getEmail())
                .firstName(registerCourier.getFirstName())
                .id(registerCourier.getUserId())
                .build();
    }

    private boolean checkDBForExistingAccount(NewUserRequest senderRequest){
        Courier checkMail = courierRepository.findCourierByEmail(senderRequest.getEmail());
        return checkMail != null;
    }

    @Override
    public LoginResponse LogIntoDashboard(LoginRequest loginRequest) {
        Courier findCourierInDB = null;
        boolean checkLoginRequest = checkLoginRequest(loginRequest);
        if (checkLoginRequest) {
            findCourierInDB = courierRepository.findCourierByEmail(loginRequest.getEmail());
        } else {
            throw new UserNotfoundException("invalid credentials");
        }
        return LoginResponse.builder()
                .Id(findCourierInDB.getUserId()).firstName(findCourierInDB.getFirstName())
                .email(findCourierInDB.getEmail())
                .build();
    }

    private boolean checkLoginRequest(LoginRequest loginRequest) {
        boolean checkMail = false;
        boolean checkPassword = false;
        Courier getCourier = (courierRepository.findCourierByEmail(loginRequest.getEmail()));
        if (getCourier != null) {
            checkMail = getCourier.getEmail().equals(loginRequest.getEmail());
            checkPassword = getCourier.getPassword().equals(loginRequest.getPassword());
        }
        return checkMail && checkPassword;
    }

    @Override
    public User getUserById(String s) {
       Courier  getCourier = courierRepository.findCourierByUserId(s);
        if(getCourier != null) return getCourier;
        throw new UserNotfoundException("user with id -> " + s + " not found");
    }

    @Override
    public NewOrderResponse sendOrder(NewOrderRequest orderRequest) {
        return null;
    }

    @Override
    public void deleteOrderById(String s) {

    }

    @Override
    public void cancelOrderById(String id) {
            orderService.cancelOrder(id);
    }

    @Override
    public DeliveryStatus checkDeliveryStatus(String id) {

        return orderService.checkDeliveryStatus(id);
    }

    @Override
    public List<Order> getAllSendersOrder(String s) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderService.getAllSendersOrders();
    }

    @Override
    public Order getOrderById(String s) {
        return orderService.getOrderById(s);
    }

    @Override
    public DeliveryStatus confirmDeliveryStatus(String id) {
        return null;
    }
}
