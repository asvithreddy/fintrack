// package com.example.fintrack.controller;

// import com.example.fintrack.service.BudgetPredictionService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/budget")
// public class BudgetController {
//     private final BudgetPredictionService budgetPredictionService;

//     @Autowired
//     public BudgetController(BudgetPredictionService budgetPredictionService) {
//         this.budgetPredictionService = budgetPredictionService;
//     }

//     @GetMapping("/fetch")
//     public double[] fetchAndPredictBudget(@RequestParam int userId) {
//         return budgetPredictionService.predictFutureBudgets(userId);
//     }
// }

package com.example.fintrack.controller;

import com.example.fintrack.dto.BudgetResponse;
import com.example.fintrack.service.BudgetPredictionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetPredictionService budgetPredictionService;

    public BudgetController(BudgetPredictionService budgetPredictionService) {
        this.budgetPredictionService = budgetPredictionService;
    }

    @GetMapping("/fetch")
    public BudgetResponse fetchAndPredictBudget(@RequestParam int userId) {
        return budgetPredictionService.predictFutureBudgets(userId);
    }
}
