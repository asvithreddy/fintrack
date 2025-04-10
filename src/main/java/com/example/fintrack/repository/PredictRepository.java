package com.example.fintrack.repository;

import com.example.fintrack.model.Predict;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictRepository extends MongoRepository<Predict, String> {
    Predict findByUserId(int userId); 
}
