package dev.construction.weddingplanner;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "items")
@Data
public class Item {
    @Id
    private ObjectId id;
    private String name;
    private String link;
    public Item(String name){
        this.name = name;
    }

    public Item(String name, String link) {
        this.name = name;
        this.link = link;
    }
}

