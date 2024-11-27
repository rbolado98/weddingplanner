package dev.construction.weddingplanner;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public boolean authenticate(String email, String password) {
        Optional<User> user = findUserByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public boolean checkIfUserExists(String email) {
        Optional<User> user = findUserByEmail(email);
        return user.isPresent();
    }
    // public User validateUser(String email, String password) {
    //     return mongoTemplate
    // }

}