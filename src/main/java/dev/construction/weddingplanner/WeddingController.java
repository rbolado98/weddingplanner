package dev.construction.weddingplanner;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weddings")
public class WeddingController {
    @Autowired
    private WeddingService weddingService;
    @GetMapping
    public ResponseEntity<List<Wedding>> getWeddings() {
        return new ResponseEntity<List<Wedding>> (weddingService.allWeddings(), HttpStatus.OK);
    } 
    @GetMapping("/{WeddingId}")
    public ResponseEntity<Optional<Wedding>> getSingleWedding(@PathVariable String WeddingId) {  
        return new ResponseEntity<Optional<Wedding>>(weddingService.singleWedding(WeddingId), HttpStatus.OK);
    }
}