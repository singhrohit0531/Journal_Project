package com.first.journalapp.repository;

import com.first.journalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true)); // we can also use REGEX instead of checking email twice
        query.addCriteria(Criteria.where("email").ne(null).ne("")); // here all 3 lines run in AND condition
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(                         // This code is used to write criteria in OR condition
//                Criteria.where("email").exists(true),
//                Criteria.where("sentimentAnalysis").is(true)));
        return mongoTemplate.find(query, User.class);
    }
}
