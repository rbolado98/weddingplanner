package dev.construction.weddingplanner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private WeddingService weddingService;
    public Item createItem(String name, String link, String weddingId) {
        Item item = itemRepository.insert(new Item(name, link));
        mongoTemplate.update(Wedding.class)
            .matching(Criteria.where("weddingId").is(weddingId))
            .apply(new Update().push("registry").value(item))
            .first();
        return item;
    }
    public List<Item> getItemsByWeddingId(String weddingId) {
        // Fetch the Wedding document by weddingId
        Wedding wedding = weddingService.singleWedding(weddingId).get();

        if (wedding == null) {
            throw new IllegalArgumentException("Wedding not found with ID: " + weddingId);
        }

        // Extract the ObjectId references from the registry field
        List<Item> itemIds = wedding.getRegistry();

        // Fetch all Item objects based on their ObjectIds
        return itemIds;
    }

}
