package services;

import data.dtos.repositories.SenderRepository;
import data.dtos.request.CreateSenderRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.LoginResponse;
import data.dtos.response.OrderResponse;
import data.dtos.response.SenderResponse;
import data.models.*;
import exceptions.UserNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OperatorInstanceof;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class SenderServiceImpl implements UserService {

    @Autowired
    private SenderRepository senderRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public SenderResponse registerSender(CreateSenderRequest senderRequest) {
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

        return SenderResponse.builder()
                .id(savedUser.getUserId())
                .firstName(savedUser.getFirstName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public LoginResponse LogIntoDashboard(LoginRequest loginRequest) {
        Sender findSenderInDB = null;
        boolean checkLoginRequest = checkLoginRequest(loginRequest);
        if (checkLoginRequest) {
            findSenderInDB = senderRepository.findSenderByEmail(loginRequest.getEmail());
        } else {
            throw new UserNotfoundException("user with the email " + loginRequest.getEmail() + " not found");
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
    public User getSenderById(String s) {
        return senderRepository.findSenderByUserId(s);
    }

    @Override
    public OrderResponse sendOrder(NewOrderRequest orderRequest) {
        Order createdOrder = orderService.saveOrder(orderRequest);
        return OrderResponse.builder()
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
        return orderService.getAllOrders(id);
    }
}


