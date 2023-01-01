package services;

import data.dtos.request.DeliveryStatusRequest;
import data.dtos.request.NewUserRequest;
import data.dtos.request.LoginRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.DeliveryStatusResponse;
import data.dtos.response.LoginResponse;
import data.dtos.response.NewOrderResponse;
import data.dtos.response.NewUserResponse;
import data.models.DeliveryStatus;
import data.models.Order;
import data.models.User;
import java.util.List;


public interface UserService {
    NewUserResponse registerUser(NewUserRequest senderRequest);
    LoginResponse LogIntoDashboard(LoginRequest loginRequest);
    User getUserById(String s);
    NewOrderResponse sendOrder(NewOrderRequest orderRequest);
    void deleteOrderById(String s);
    void cancelOrderById(String s);
    DeliveryStatus checkDeliveryStatus(String s);
    List<Order> getAllSendersOrder(String s);
    List<Order> getAllOrders();
    Order getOrderById(String s);
    DeliveryStatusResponse confirmDeliveryStatus(DeliveryStatusRequest req);
}
