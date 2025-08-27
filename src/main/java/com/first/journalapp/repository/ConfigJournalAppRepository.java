package com.first.journalapp.repository;

import com.first.journalapp.entity.ConfigJournalAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, String> {

}
