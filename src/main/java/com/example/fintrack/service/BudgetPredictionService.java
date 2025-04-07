// package com.example.fintrack.service;

// import com.example.fintrack.model.Budget;
// import com.example.fintrack.repository.BudgetRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.math.BigDecimal;
// import java.util.List;


// @Service
// public class BudgetPredictionService {
//     private final BudgetRepository budgetRepository;

//     @Autowired
//     public BudgetPredictionService(BudgetRepository budgetRepository) {
//         this.budgetRepository = budgetRepository;
//     }

//     public Budget getBudgetByUserId(int userId) {
//         return budgetRepository.findByUserId(userId);
//     }

//     public double[] predictFutureBudgets(int userId) {
//         Budget budget = getBudgetByUserId(userId);
//         if (budget == null) {
//             throw new RuntimeException("User not found!");
//         }

//         double currentBudget = budget.getYearlyBudget();
//         double[] futureBudgets = new double[3];

//         // Predicting the next 3 years with inflation (6%, 7%, 8%)
//         double[] inflationRates = {0.06, 0.07, 0.08};
//         for (int i = 0; i < 3; i++) {
//             currentBudget += currentBudget * inflationRates[i];
//             futureBudgets[i] = currentBudget;
//         }

//         return futureBudgets;
//     }
// }

package com.example.fintrack.service;

import com.example.fintrack.dto.BudgetResponse;
import com.example.fintrack.model.Budget;
import com.example.fintrack.repository.BudgetRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetPredictionService {

    private final BudgetRepository budgetRepository;

    public BudgetPredictionService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public BudgetResponse predictFutureBudgets(int userId) {
        Budget budget = budgetRepository.findByUserId(userId);
        if (budget == null) {
            throw new RuntimeException("User not found");
        }

        double currentBudget = budget.getYearlyBudget();
        double[] futureBudgets = new double[3];

        // Predicting the next 3 years with inflation
        double[] inflationRates = {0.06, 0.07, 0.08};
        for (int i = 0; i < 3; i++) {
            currentBudget += currentBudget * inflationRates[i];
            futureBudgets[i] = currentBudget;
        }

        // Return a BudgetResponse object
        return new BudgetResponse(budget.getYearlyBudget(), futureBudgets);
    }
}
