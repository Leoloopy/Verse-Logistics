package services;

import data.dtos.repositories.SenderRepository;
import data.dtos.request.DeliveryStatusRequest;
import data.dtos.request.NewUserRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.DeliveryStatusResponse;
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
@Qualifier("sender")
public class SenderServiceImpl implements UserService {

    @Autowired
    private SenderRepository senderRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public NewUserResponse registerUser(NewUserRequest senderRequest) {

        if (checkDBForExistingAccount(senderRequest))
            throw new UserAlreadyExistException("user with " + senderRequest.getEmail() + " already exist");

        HashSet<Address> senderAddress = new HashSet<>();
        senderAddress.add(senderRequest.getAddress());

        Sender sender = new Sender();
        sender.setFirstName(senderRequest.getFirstName());
        sender.setLastName(senderRequest.getLastName());
        sender.setEmail(senderRequest.getEmail());
        sender.setPassword(senderRequest.getPassword());
        sender.setPhoneNumber(senderRequest.getPhoneNumber());
        sender.setAddress(senderAddress);
        sender.setCreationDate(LocalDateTime.now());

        User savedUser = senderRepository.save(sender);

        return NewUserResponse.builder()
                .id(savedUser.getUserId())
                .firstName(savedUser.getFirstName())
                .email(savedUser.getEmail())
                .build();
    }

    private boolean checkDBForExistingAccount(NewUserRequest senderRequest){
        Sender checkMail = senderRepository.findSenderByEmail(senderRequest.getEmail());
        return checkMail != null;
    }

    @Override
    public LoginResponse LogIntoDashboard(LoginRequest loginRequest) {
        Sender findSenderInDB = null;
        boolean checkLoginRequest = checkLoginRequest(loginRequest);
        if (checkLoginRequest) {
            findSenderInDB = senderRepository.findSenderByEmail(loginRequest.getEmail());
        } else {
            throw new UserNotfoundException("invalid credentials");
        }
        return LoginResponse.builder()
                .Id(findSenderInDB.getUserId())
                .firstName(findSenderInDB.getFirstName())
                .email(findSenderInDB.getEmail())
                .build();
    }

    private boolean checkLoginRequest(LoginRequest loginRequest) {
        boolean checkMail = false;
        boolean checkPassword = false;
        Sender getSender = (senderRepository.findSenderByEmail(loginRequest.getEmail()));
        if (getSender != null) {
            checkMail = getSender.getEmail().equals(loginRequest.getEmail());
            checkPassword = getSender.getPassword().equals(loginRequest.getPassword());
        }
        return checkMail && checkPassword;
    }


    @Override
    public User getUserById(String s) {
        User getUser =  senderRepository.findSenderByUserId(s);
        if(getUser != null) return getUser;
        throw new UserNotfoundException("user with id -> " + s + " not found");
    }

    @Override
    public NewOrderResponse sendOrder(NewOrderRequest orderRequest) {
        Order createdOrder = orderService.saveOrder(orderRequest);
        return NewOrderResponse.builder()
                .orderId(createdOrder.getOrderId())
                .userId(createdOrder.getSenderId())
                .receiverName(createdOrder.getReceiverName())
                .orderDate(createdOrder.getCreationDate())
                .deliveryStatus(createdOrder.getDeliveryStatus())
                .build();
    }

    @Override
    public void deleteOrderById(String id) {
        orderService.deleteOrderById(id);
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
    public List<Order> getAllSendersOrder(String id) {
        return orderService.getAllOrdersBySenderId(id);
    }

    // NO IMPLEMENTATION --> SPECIFIC TO COURIER SERVICE
    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order getOrderById(String s) {
        return null;
    }

    @Override
    public DeliveryStatusResponse confirmDeliveryStatus(DeliveryStatusRequest req) {
        return null;
    }

}


