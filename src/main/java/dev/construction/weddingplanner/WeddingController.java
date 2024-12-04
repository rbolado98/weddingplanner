package dev.construction.weddingplanner;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weddings")
@CrossOrigin(origins = "*")
public class WeddingController {
    @Autowired
    private WeddingService weddingService;

    // @PostMapping
    // public ResponseEntity<Wedding> createWedding(@RequestBody Map<String, String> payload) {
    //     return new ResponseEntity<Wedding>(weddingService.createWedding(), HttpStatus.CREATED);
    // }
    @GetMapping
    public ResponseEntity<List<Wedding>> getWeddings() {
        return new ResponseEntity<List<Wedding>> (weddingService.allWeddings(), HttpStatus.OK);
    } 
    @GetMapping("/created/{email}")
    public ResponseEntity<List<Optional<Wedding>>> getWeddingsForUser(@PathVariable String email) {
        return new ResponseEntity<List<Optional<Wedding>>>(weddingService.getWeddingsForUser(email), HttpStatus.OK);
    }
    @GetMapping("/attending/{email}")
    public ResponseEntity<List<Optional<Wedding>>> getAttendingWeddings(@PathVariable String email) {
        return new ResponseEntity<List<Optional<Wedding>>>(weddingService.getAttendingWeddings(email), HttpStatus.OK);
    }
    @GetMapping("/{WeddingId}")
    public ResponseEntity<Optional<Wedding>> getSingleWedding(@PathVariable String WeddingId) {  
        return new ResponseEntity<Optional<Wedding>>(weddingService.singleWedding(WeddingId), HttpStatus.OK);
    }
    @PostMapping("/addInvited")
    public ResponseEntity<Wedding> addInvited(@RequestParam String name, @RequestParam String weddingId) {  
        return new ResponseEntity<Wedding>(weddingService.addInvited(name, weddingId), HttpStatus.CREATED);
    }
    @PostMapping("/addToWaitlist")
    public ResponseEntity<Wedding> addToWaitlist(@RequestParam String name, @RequestParam String weddingId) {  
        return new ResponseEntity<Wedding>(weddingService.addToWaitlist(name, weddingId), HttpStatus.CREATED);
    }
    @GetMapping("/getAttendees/{weddingId}")
    public ResponseEntity<List<User>> getAttendees(@PathVariable String weddingId) {
        return new ResponseEntity<List<User>>(weddingService.singleWedding(weddingId).get().getAttendees(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{weddingId}")
    public ResponseEntity<String> deleteWedding(@PathVariable String weddingId) {
        weddingService.deleteWedding(weddingId);
        return new ResponseEntity<String>("Wedding deleted", HttpStatus.OK);
    }

    @PostMapping("/createwedding")
    public ResponseEntity<Wedding> createWedding(@RequestParam String weddingTitle, @RequestParam String location, @RequestParam String dateTime, @RequestParam String maxAttendees, @RequestParam String email) {  
        return new ResponseEntity<Wedding>(weddingService.createWedding(weddingTitle, dateTime, location, maxAttendees, email), HttpStatus.CREATED);
    }

}