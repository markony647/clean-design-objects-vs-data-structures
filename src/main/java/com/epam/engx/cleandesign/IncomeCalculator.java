package com.epam.engx.cleandesign;

import java.util.List;

public class IncomeCalculator {

    private final RevenueCalculator revenueCalculator;
    private final ExpensesCalculator expensesCalculator;

    public IncomeCalculator(RevenueCalculator revenueCalculator, ExpensesCalculator expensesCalculator) {
        this.revenueCalculator = revenueCalculator;
        this.expensesCalculator = expensesCalculator;
    }

    public double calculate(List<Assignment> assignments) {
        return calculateRevenue(assignments) - calculateExpenses(assignments);
    }

    private double calculateRevenue(List<Assignment> assignments) {
        return assignments.stream()
                .map(revenueCalculator::calculate)
                .reduce(0.0, Double::sum);
    }

    private double calculateExpenses(List<Assignment> assignments) {
        return assignments.stream()
                .map(expensesCalculator::calculate)
                .reduce(0.0, Double::sum);
    }
}
