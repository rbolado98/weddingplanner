package dev.construction.weddingplanner;

import java.util.List;
import java.util.Map;
// import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RestController
@RequestMapping("/api/v1/weddings")
@CrossOrigin(origins = "*")
public class WeddingController {
    @Autowired
    private WeddingService weddingService;

    @Autowired
    private UserService userService;

    // @PostMapping
    // public ResponseEntity<Wedding> createWedding(@RequestBody Map<String, String> payload) {
    //     return new ResponseEntity<Wedding>(weddingService.createWedding(), HttpStatus.CREATED);
    // }
    @GetMapping
    public ResponseEntity<List<Wedding>> getWeddings() {
        return new ResponseEntity<List<Wedding>> (weddingService.allWeddings(), HttpStatus.OK);
    } 
    @GetMapping("/{WeddingId}")
    public ResponseEntity<Optional<Wedding>> getSingleWedding(@PathVariable String WeddingId) {  
        return new ResponseEntity<Optional<Wedding>>(weddingService.singleWedding(WeddingId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Wedding> createWedding(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<Wedding>(weddingService.createWedding(payload.get("weddingId"), payload.get("weddingTitle"), payload.get("dateTime"), payload.get("location"), payload.get("maxAttendees")), HttpStatus.CREATED);
        // return new ResponseEntity<Wedding>(weddingService.createWedding("weddingId", "weddingTitle", "dateTime", "location", "maxAtt"), HttpStatus.CREATED);

    }
    
    //RSVP to a wedding
    @PostMapping("/{weddingId}/rsvp")
    public ResponseEntity<String> rsvpToWedding(@PathVariable String weddingId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        Optional<User> userOptional = userService.findUserByName(username);
        
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();

        boolean rsvpSuccess = weddingService.rsvpToWedding(weddingId, user);
        if (rsvpSuccess){
            return new ResponseEntity<>("RSVP successful!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("RSVP failed or already registered.", HttpStatus.BAD_REQUEST);
        }

    }
}