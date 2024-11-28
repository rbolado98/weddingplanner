package dev.construction.weddingplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registries")
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    //Add item to a registry
    @PostMapping("/{registryId}/items")
    public String addItemToRegistry(@PathVariable String registryId, @RequestParam String name, @RequestParam String purchaseLink){
        boolean success = registryService.addItemToRegistry(registryId, name, purchaseLink);
        return success ? "Item added successfully" : "Registry not found";
    }
    
}
