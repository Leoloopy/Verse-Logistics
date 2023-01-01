package services;

import data.dtos.request.DeliveryStatusRequest;
import data.dtos.request.NewOrderRequest;
import data.dtos.response.DeliveryStatusResponse;
import data.models.DeliveryStatus;
import data.models.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(NewOrderRequest newOrderRequest);
    void deleteOrderById(String Id);
    DeliveryStatus checkDeliveryStatus(String orderId);
    Order getOrderById(String Id);
    void cancelOrder(String id);
    DeliveryStatusResponse confirmDeliveryStatus(DeliveryStatusRequest request);
    void deleteAllOrders();
    List<Order> getAllOrdersBySenderId(String s);
    List<Order> getAllSendersOrders();
}