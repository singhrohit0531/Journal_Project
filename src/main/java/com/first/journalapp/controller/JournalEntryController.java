package com.first.journalapp.controller;

import com.first.journalapp.entity.JournalEntry;
import com.first.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping // if no path is given then it'll be called for GET api
    public List<JournalEntry>getAll(){
        return journalEntryService.getAll();
    }


    @PostMapping // if no path is given then it'll be called for POST api
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myentry){ //localhost:8080/journal
        try {
            myentry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myentry);
            return new ResponseEntity<>(myentry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myid) {
        Optional<JournalEntry> journalEntry =journalEntryService.findById(myid);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myid){
        journalEntryService.deleteById(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newentry) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old != null){
            old.setTitle(newentry.getTitle() != null && !newentry.getTitle().equals("")? newentry.getTitle() : old.getTitle() );
            old.setContent(newentry.getContent() != null && !newentry.getContent().equals("") ? newentry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
