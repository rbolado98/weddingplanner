package dev.construction.weddingplanner;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "weddings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wedding {

    @Id
    private ObjectId id;
    private String weddingId;
    private String weddingTitle;
    private String dateTime;
    private String location;
    private User createdBy;
    @DocumentReference //This makes the database store only the ids of the comments and the comments themselves will be stored in a separate collection
    private List<User> attendees;
    private int maxAttendees;
    private List<String> waitlist;
    @DocumentReference //This makes the database store only the ids of the comments and the comments themselves will be stored in a separate collection
    private List<Item> registry;
    private List<String> invited;
    @DocumentReference //This makes the database store only the ids of the comments and the comments themselves will be stored in a separate collection
    private List<Comment> commentIds;

    public Wedding(String weddingId, String weddingTitle, String dateTime, String location, User createdBy, int maxAttendees) {
        this.weddingId = weddingId;
        this.weddingTitle = weddingTitle;
        this.dateTime = dateTime;
        this.location = location;
        this.createdBy = createdBy;
        this.maxAttendees = maxAttendees;
    }
}