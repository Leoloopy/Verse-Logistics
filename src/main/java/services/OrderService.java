package services;

import data.dtos.request.NewOrderRequest;
import data.models.Address;
import data.models.DeliveryStatus;
import data.models.Item;
import data.models.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(NewOrderRequest newOrderRequest);
    void deleteOrderById(String Id);
    DeliveryStatus checkDeliveryStatus(String orderId);
    Order getOrderById(String Id);
    void cancelOrder(String id);
    void deleteAllOrders();
    List<Order> getAllOrders(String s);
}