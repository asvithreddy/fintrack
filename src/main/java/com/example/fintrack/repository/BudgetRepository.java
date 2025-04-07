package com.example.fintrack.repository;

import com.example.fintrack.model.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends MongoRepository<Budget, String> {
    Budget findByUserId(int userId); // Custom query to fetch by userId
}
