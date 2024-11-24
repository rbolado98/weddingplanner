package dev.construction.weddingplanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "comments")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Comment {
    @Id
    private ObjectId id;
    private String body;
    
    public Comment(String body) {
        this.body = body;
    }
}
