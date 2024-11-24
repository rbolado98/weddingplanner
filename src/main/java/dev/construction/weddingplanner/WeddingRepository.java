package dev.construction.weddingplanner;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeddingRepository extends MongoRepository<Wedding, ObjectId> {
    Optional<Wedding> findWeddingByWeddingId(String weddingId);
    
}
