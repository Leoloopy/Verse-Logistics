package services;


import data.dtos.repositories.OrderRepository;
import data.dtos.request.NewOrderRequest;
import data.models.Address;
import data.models.DeliveryStatus;
import data.models.Item;
import data.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order saveOrder(NewOrderRequest newOrderRequest) {
        Address deliveryAddress = Address.builder()
                .street(newOrderRequest.getReceiverAddress().getStreet())
                .city(newOrderRequest.getReceiverAddress().getCity())
                .state(newOrderRequest.getReceiverAddress().getState())
                .country(newOrderRequest.getReceiverAddress().getCountry())
                .build();

        List<Item> items = new ArrayList<>(newOrderRequest.getItem());

        Order savedOrder = Order.builder()
                .receiverAddress(deliveryAddress)
                .creationDate(this.setLocalDateTime())
                .deliveryStatus(DeliveryStatus.PROCESSING)
                .receiverName(newOrderRequest.getReceiverName())
                .receiverPhoneNumber(newOrderRequest.getReceiverPhoneNumber())
                .item(items)
                .senderId(newOrderRequest.getUserId())
                .build();

        return orderRepository.save(savedOrder);
    }

    private LocalDateTime setLocalDateTime() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String text = date.format(formatter);
        return LocalDateTime.parse(text, formatter);
    }



    @Override
    public void deleteOrderById(String Id) {
        orderRepository.deleteOrderByOrderId(Id);
    }

    @Override
    public List<Order> getAllOrders(String userId) {
        return  orderRepository.findOrdersBySenderId(userId);
    }

    @Override
    public DeliveryStatus checkDeliveryStatus(String orderId) {
        Order findOrder = orderRepository.findOrderByOrderId(orderId);
        return findOrder.getDeliveryStatus();
    }

    @Override
    public Order getOrderById(String Id) {
        return orderRepository.findOrderByOrderId(Id);
    }

    @Override
    public void cancelOrder(String id) {
       Order findOrder = orderRepository.findOrderByOrderId(id);
        findOrder.setDeliveryStatus(DeliveryStatus.ON_HOLD);
        orderRepository.save(findOrder);
    }

    @Override
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
