package dev.construction.weddingplanner;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<Item>(itemService.createItem(payload.get("name"), payload.get("link"), payload.get("weddingId")), HttpStatus.CREATED);
    }
}