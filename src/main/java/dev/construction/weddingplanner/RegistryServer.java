package dev.construction.weddingplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistryServer {
    
    @Autowired
    private RegistryRepository RegistryRepository;

    public boolean addItemToRegistry(String registryId, String name, String purchaseLink){
        Registry registry = RegistryRepository.findById(registryId).orElse(null);
        if (registry != null){
            registry.addItem(name, purchaseLink);
            RegistryRepository.save(registry);
            return true;
        }
        return false;
    }
}
