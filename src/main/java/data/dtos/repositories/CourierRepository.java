package data.dtos.repositories;

import data.dtos.response.NewOrderResponse;
import data.models.Courier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends MongoRepository<Courier, String> {
    Courier findCourierByUserId(String s);
    Courier findCourierByEmail(String s);
}
