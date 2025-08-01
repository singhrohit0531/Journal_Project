package com.first.journalapp.repository;

import com.first.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {


    String Id(ObjectId id);

    List<JournalEntry> id(ObjectId id);
}
