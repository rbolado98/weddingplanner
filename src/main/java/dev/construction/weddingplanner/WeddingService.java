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

    public Wedding createWedding(String weddingId, String weddingTitle, String dateTime, String location, String maxAttendees) {
        Wedding wedding = new Wedding(weddingId, weddingTitle, dateTime, location, maxAttendees);
        mongoTemplate.insert(wedding, "weddings");
        return wedding;
    }

    public Wedding createWeddingAll(String weddingId, String weddingTitle, String dateTime, String location, User createdBy, String maxAttendees, List<String> waitlist, List<String> invited) {
        Wedding wedding = new Wedding(weddingId, weddingTitle, dateTime, location, createdBy, maxAttendees, waitlist, invited);
        mongoTemplate.insert(wedding, "weddings");
        return wedding;
    }

    public boolean rsvpToWedding(String weddingId, User user){
        Optional<Wedding> weddingOptional = weddingRepository.findWeddingByWeddingId(weddingId);
        Wedding wedding = weddingOptional.get();

        if (weddingOptional.isPresent()){
            // wedding = weddingOptional.get();
            int maxAttendees = Integer.parseInt(wedding.getMaxAttendees());

            // Check if the user is already an attendee or in the waitlist
            if (wedding.getAttendees().contains(user)) return false;
            if (wedding.getWaitlist().contains(user.getId().toHexString())) return false;

            if(wedding.getAttendees().size() < maxAttendees){
                wedding.getAttendees().add(user);
            } else {
                wedding.getWaitlist().add(user.getId().toHexString());
            }

        }
        weddingRepository.save(wedding);
        return true;
    } 

    public boolean addItemToRegistry(String weddingId, String itemName, int quantity){
        Optional<Wedding> weddingOptional = weddingRepository.findWeddingByWeddingId(weddingId);

        if (weddingOptional.isPresent()){
            Wedding wedding = weddingOptional.get();
            RegistryItem newItem = new RegistryItem(itemName, quantity);
            wedding.getRegistry().add(newItem);
            weddingRepository.save(wedding);
            return true;
        }
        return false;
    }
}
