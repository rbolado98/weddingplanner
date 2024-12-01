package dev.construction.weddingplanner;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private ObjectId id;
    private String name;
    private String link;
    // private boolean purchased;


    public Item(String name){
        this.name = name;
    }
    public Item(String name, String link) {
        this.name = name;
        this.link = link;
    }
}

