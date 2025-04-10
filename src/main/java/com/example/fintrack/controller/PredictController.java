package com.example.fintrack.controller;

import com.example.fintrack.dto.BudgetResponse;
import com.example.fintrack.service.BudgetPredictionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class PredictController {

    private final BudgetPredictionService budgetPredictionService;

    public PredictController(BudgetPredictionService budgetPredictionService) {
        this.budgetPredictionService = budgetPredictionService;
    }

    @GetMapping("/fetch")
    public BudgetResponse fetchAndPredictBudget() {
        return budgetPredictionService.predictWithoutUserId(); // new method
    }
}
