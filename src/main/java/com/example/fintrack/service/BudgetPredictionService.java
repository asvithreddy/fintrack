
package com.example.fintrack.service;

import com.example.fintrack.dto.BudgetResponse;
import com.example.fintrack.model.Predict;
import com.example.fintrack.repository.PredictRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetPredictionService {

    private final PredictRepository predictRepository;

    public BudgetPredictionService(PredictRepository predictRepository) {
        this.predictRepository = predictRepository;
    }

    public BudgetResponse predictFutureBudgets(String userId) {
        Predict predict = predictRepository.findByUserId(userId);
        if (predict == null) {
            throw new RuntimeException("User not found");
        }

        double currentBudget = predict.getYearlyBudget();
        double[] futureBudgets = new double[3];
        double[] inflationRates = {0.06, 0.07, 0.08};

        for (int i = 0; i < 3; i++) {
            currentBudget += currentBudget * inflationRates[i];
            futureBudgets[i] = currentBudget;
        }

        return new BudgetResponse(predict.getYearlyBudget(), futureBudgets);
    }
}
