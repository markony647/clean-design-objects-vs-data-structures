package com.epam.engx.cleandesign;

public class ExpensesCalculator {

    public double calculateAssignmentSalaryExpensesWithBonus(Assignment assignment) {
        return assignment.calculateSalaryFund() + assignment.getBonus();
    }
}
