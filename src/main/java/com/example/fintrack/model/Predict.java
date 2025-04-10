package com.example.fintrack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "budget") // MongoDB collection name
public class Predict {
    @Id
    private String id; // MongoDB document ID
    private int userId;
    private double yearlyBudget;

    // Constructors
    public Predict() {}

    public Predict(int userId, double yearlyBudget) {
        this.userId = userId;
        this.yearlyBudget = yearlyBudget;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getYearlyBudget() {
        return yearlyBudget;
    }

    public void setYearlyBudget(double yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
    }
}
