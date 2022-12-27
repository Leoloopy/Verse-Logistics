package services;

import data.dtos.request.CreateSenderRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.LoginResponse;
import data.dtos.response.OrderResponse;
import data.dtos.response.SenderResponse;
import data.models.DeliveryStatus;
import data.models.Order;
import data.models.User;
import java.util.List;


public interface UserService {
    SenderResponse registerSender(CreateSenderRequest senderRequest);
    LoginResponse LogIntoDashboard(LoginRequest loginRequest);
    User getSenderById(String s);
    OrderResponse sendOrder(NewOrderRequest orderRequest);
    void deleteOrderById(String s);
    void cancelOrderById(String s);
    DeliveryStatus checkDeliveryStatus(String s);
    List<Order> getAllSendersOrder(String s);

}
