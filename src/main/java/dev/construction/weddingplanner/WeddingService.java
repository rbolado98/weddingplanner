package dev.construction.weddingplanner;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class WeddingService {
    @Autowired
    private WeddingRepository weddingRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Wedding> allWeddings() {
        return weddingRepository.findAll();
    }

    public Optional<Wedding> singleWedding(String WeddingId) {
        return weddingRepository.findWeddingByWeddingId(WeddingId);
    }

    public Wedding createWedding(String weddingTitle, String dateTime, String location, String maxAttendees) {
        Wedding wedding = new Wedding(weddingTitle, dateTime, location, maxAttendees);
        mongoTemplate.insert(wedding, "weddings");
        return wedding;
    }

    @Autowired
    private UserRepository userRepository;
    public User attendWedding(String weddingId, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail).get();
        mongoTemplate.update(Wedding.class)
            .matching(Criteria.where("weddingId").is(weddingId))
            .apply(new Update().push("attendees").value(user))
            .first();
        return user;
    }
}
