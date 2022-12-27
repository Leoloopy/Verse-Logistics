package data.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Sender extends User{

}
