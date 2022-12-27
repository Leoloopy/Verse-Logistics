package data.dtos.repositories;

import data.models.Sender;
import data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends MongoRepository<Sender, String> {
    User findUserByPhoneNumber(String s);
//    User findUserByUserId(String id);
    Sender findSenderByUserId(String s);
    Sender findSenderByEmail(String s);
}
