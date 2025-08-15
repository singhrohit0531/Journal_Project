package com.first.journalapp.service;

import com.first.journalapp.entity.JournalEntry;
import com.first.journalapp.entity.User;
import com.first.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional // It is used to execute the entire function all together.
    // If any one line fails to execute things that were executed will be reverted
    public void saveEntry(JournalEntry journalEntry, String username){
        try{
            journalEntry.setDate(LocalDateTime.now());
            User user = userService.findByUsername(username);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveNewUser(user);
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the message",e);
        }

    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(String.valueOf(id));
    }

    @Transactional
    public void deleteById(ObjectId id, String username){
        try {
            User user = userService.findByUsername(username);
            boolean remove = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(remove){
                userService.saveNewUser(user);
                journalEntryRepository.deleteById(String.valueOf(id));
        }

       } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error occured while deleting" , e);
        }
    }
}
