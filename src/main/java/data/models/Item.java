package data.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Item {
    private int id;
    private int weight;
    private String name;
    private LocalDateTime localDateTime;
}
