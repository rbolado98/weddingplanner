package dev.construction.weddingplanner;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistryRepository extends MongoRepository<Registry, ObjectId>  {
    Optional<Registry> findById(String id);
    
    
    
}