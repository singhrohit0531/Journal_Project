package com.first.journalapp.controller;

import com.first.journalapp.api_response.WeatherResponse;
import com.first.journalapp.entity.User;
import com.first.journalapp.repository.UserRepository;
import com.first.journalapp.service.UserService;
import com.first.journalapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final WeatherService weatherService;

    public UserController(UserService userService, UserRepository userRepository, WeatherService weatherService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.weatherService = weatherService;
    }

    @PutMapping
    public ResponseEntity<?> updateUser (@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User UserInDB = userService.findByUsername(username);
        if (UserInDB != null){
            UserInDB.setUsername(user.getUsername());
            UserInDB.setPassword(user.getPassword());
            userService.saveUser(UserInDB);
        }
        return new  ResponseEntity<> (HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse= weatherService.getWeather("Patna");
        String greeting ="";
        if(weatherResponse != null){
            greeting=", Weather feels like "+ weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+ authentication.getName() + greeting, HttpStatus.OK);
    }

}
