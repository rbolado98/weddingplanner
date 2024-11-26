package dev.construction.weddingplanner;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    public User createUser(String name, String email, String password) {
        User user = new User(name, email, password);
        mongoTemplate.insert(user, "users");
        return user;
    }

}