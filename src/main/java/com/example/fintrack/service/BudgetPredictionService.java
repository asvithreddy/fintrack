// // package com.example.fintrack.service;

// // import com.example.fintrack.dto.BudgetResponse;
// // import com.example.fintrack.model.Predict;
// // import com.example.fintrack.repository.PredictRepository;
// // import org.springframework.stereotype.Service;

// // import java.util.List;

// // @Service
// // public class BudgetPredictionService {

// //     private final PredictRepository budgetRepository;

// //     public BudgetPredictionService(PredictRepository budgetRepository) {
// //         this.budgetRepository = budgetRepository;
// //     }

// //     // New method: fetches first available budget entry
// //     public BudgetResponse predictWithoutUserId() {
// //         List<Predict> allBudgets = budgetRepository.findAll();

// //         if (allBudgets.isEmpty()) {
// //             throw new RuntimeException("No budget data available");
// //         }

// //         double currentBudget = allBudgets.get(0).getYearlyBudget(); // first document
// //         double[] futureBudgets = new double[3];
// //         double[] inflationRates = {0.06, 0.07, 0.08};

// //         double temp = currentBudget;
// //         for (int i = 0; i < 3; i++) {
// //             temp += temp * inflationRates[i];
// //             futureBudgets[i] = Math.round(temp * 100.0) / 100.0; // rounding to 2 decimals
// //         }

// //         return new BudgetResponse(currentBudget, futureBudgets);
// //     }
// // }
// package com.example.fintrack.service;

// import com.example.fintrack.dto.BudgetResponse;
// import com.example.fintrack.model.Predict;
// import com.example.fintrack.repository.PredictRepository;


// import org.springframework.stereotype.Service;

// @Service
// public class BudgetPredictionService {

//     private final PredictRepository budgetRepository;

//     public BudgetPredictionService(PredictRepository budgetRepository) {
//         this.budgetRepository = budgetRepository;
//     }

//     public BudgetResponse predictFutureBudgets(int userId) {
//         Predict budget = budgetRepository.findByUserId(userId);
//         if (budget == null) {
//             throw new RuntimeException("User not found");
//         }

//         double currentBudget = budget.getYearlyBudget();
//         double[] futureBudgets = new double[3];

//         // Predicting the next 3 years with inflation
//         double[] inflationRates = {0.06, 0.07, 0.08};
//         for (int i = 0; i < 3; i++) {
//             currentBudget += currentBudget * inflationRates[i];
//             futureBudgets[i] = currentBudget;
//         }

//         // Return a BudgetResponse object
//         return new BudgetResponse(budget.getYearlyBudget(), futureBudgets);
//     }
// }
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
