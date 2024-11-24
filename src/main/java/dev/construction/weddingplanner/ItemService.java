package dev.construction.weddingplanner;

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
    public Item createItem(String name, String link, String weddingId) {
        Item item = itemRepository.insert(new Item(name, link));
        mongoTemplate.update(Wedding.class)
            .matching(Criteria.where("weddingId").is(weddingId))
            .apply(new Update().push("registry").value(item))
            .first();
        return item;
    }

}
