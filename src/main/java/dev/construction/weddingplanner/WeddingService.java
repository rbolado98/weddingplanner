package dev.construction.weddingplanner;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
}
