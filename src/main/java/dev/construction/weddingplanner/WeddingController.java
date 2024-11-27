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
    @GetMapping("/{WeddingId}")
    public ResponseEntity<Optional<Wedding>> getSingleWedding(@PathVariable String WeddingId) {  
        return new ResponseEntity<Optional<Wedding>>(weddingService.singleWedding(WeddingId), HttpStatus.OK);
    }
    @PostMapping("/createwedding")
    public ResponseEntity<Wedding> createWedding(@RequestParam String weddingTitle, @RequestParam String location, @RequestParam String dateTime, @RequestParam String maxAttendees) {
        return new ResponseEntity<Wedding>(weddingService.createWedding(weddingTitle, dateTime, location, maxAttendees), HttpStatus.CREATED);
        // return new ResponseEntity<Wedding>(weddingService.createWedding("weddingId", "weddingTitle", "dateTime", "location", "maxAtt"), HttpStatus.CREATED);

    }
}