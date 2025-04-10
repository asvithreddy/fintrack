package com.example.fintrack.service;

import com.example.fintrack.dto.BudgetResponse;
import com.example.fintrack.model.Predict;
import com.example.fintrack.repository.PredictRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetPredictionService {

    private final PredictRepository budgetRepository;

    public BudgetPredictionService(PredictRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    // New method: fetches first available budget entry
    public BudgetResponse predictWithoutUserId() {
        List<Predict> allBudgets = budgetRepository.findAll();

        if (allBudgets.isEmpty()) {
            throw new RuntimeException("No budget data available");
        }

        double currentBudget = allBudgets.get(0).getYearlyBudget(); // first document
        double[] futureBudgets = new double[3];
        double[] inflationRates = {0.06, 0.07, 0.08};

        double temp = currentBudget;
        for (int i = 0; i < 3; i++) {
            temp += temp * inflationRates[i];
            futureBudgets[i] = Math.round(temp * 100.0) / 100.0; // rounding to 2 decimals
        }

        return new BudgetResponse(currentBudget, futureBudgets);
    }
}
