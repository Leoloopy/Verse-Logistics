package data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@EqualsAndHashCode(callSuper = true)
public class Courier extends User{
}
