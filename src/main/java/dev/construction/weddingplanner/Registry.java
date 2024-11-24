package dev.construction.weddingplanner;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "registries")
@Data
@AllArgsConstructor
public class Registry {
    @Id
    private ObjectId id;
    private List<Item> items;

    // Constructor
    public Registry() {
        this.items = new ArrayList<>();
    }

    // Add an item to the registry
    public void addItem(String name, String purchaseLink) {
        Item newItem = new Item(name, purchaseLink);
        items.add(newItem);
        System.out.println("Item added: " + newItem);
    }

    // Remove an item by name
    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(name));
        System.out.println("Item removed: " + name);
    }

    // Get all items in the registry
    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy to prevent external modification
    }

    // Display all items
    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("The registry is empty.");
        } else {
            System.out.println("Registry Items:");
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }
}

