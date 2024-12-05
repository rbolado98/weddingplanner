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
    private String itemId;
    private String name;
    private String link;
    private boolean purchased;

    public Item(String name, String link) {
        this.itemId = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.link = link;
        this.purchased = false;

    }
    public Item(String itemId, String name, String link, boolean purchased) {
        this.itemId = itemId;
        this.name = name;
        this.link = link;
        this.purchased = purchased;

    }
    public Item(String name, String link, boolean purchased) {
        this.name = name;
        this.link = link;
        this.purchased = purchased;
    }
}

