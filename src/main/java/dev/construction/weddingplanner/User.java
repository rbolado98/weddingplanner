package dev.construction.weddingplanner;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "weddings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private ObjectId id;
    private String userId;
    private String name;
    private String email;
    private boolean isAdmin;
    // Lists of weddings
    // private List<Wedding> createdWeddings;
    // private List<Wedding> attendingWeddings;
}

