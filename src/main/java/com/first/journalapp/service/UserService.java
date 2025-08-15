package com.first.journalapp.service;

import com.first.journalapp.repository.UserRepository;
import com.first.journalapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;



    public boolean saveUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.info("All Good Open Postman");
            log.warn("All Good Open Postman");
            log.error("All Good Open Postman");
            log.debug("All Good Open Postman");
            log.trace("All Good Open Postman");
            return false;
        }
    }
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(String.valueOf(id));
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(String.valueOf(id));
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
