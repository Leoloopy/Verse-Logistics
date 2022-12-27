package data.dtos.repositories;

import data.models.DeliveryStatus;
import data.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Order findOrderByOrderId(String s);
    void deleteOrderByOrderId(String s);
    List<Order> findOrdersBySenderId(String s);
}

