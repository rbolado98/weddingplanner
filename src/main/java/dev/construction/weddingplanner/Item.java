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

    // Constructor
    public Item(String name, String purchaseLink) {
        this.name = name;
        this.link = purchaseLink;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchaseLink() {
        return link;
    }

    public void setPurchaseLink(String purchaseLink) {
        this.link = purchaseLink;
    }

    @Override
    public String toString() {
        return "Item{name='" + name + "', purchaseLink='" + link + "'}";
    }
}

