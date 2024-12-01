package dev.construction.weddingplanner;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/items")
@CrossOrigin(origins = "*")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @PostMapping
    public ResponseEntity<Item> createItemInWed(@RequestParam String name, @RequestParam String link, @RequestParam String weddingId) {
        return new ResponseEntity<Item>(itemService.createItem(name, link, weddingId), HttpStatus.CREATED);
    }
    @PostMapping("/createitem")
    public ResponseEntity<Item> createItem(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<Item>(itemService.createItem(payload.get("name"), payload.get("link"), payload.get("weddingId")), HttpStatus.CREATED);
    }
    @GetMapping("/{WeddingId}")
    public ResponseEntity<List<Item>> getItemsById(@PathVariable String WeddingId) {
        return new ResponseEntity<List<Item>>(itemService.getItemsByWeddingId(WeddingId), HttpStatus.OK);
    }
    // @GetMapping("/{WeddingId}")
    // public ResponseEntity<List<Item>> getItemsById(@RequestBody Map<String, String> payload) {
    //     return new ResponseEntity<List<Item>>(itemService.getItemsByWeddingId(payload.get("WeddingId")), HttpStatus.OK);
    // }
}