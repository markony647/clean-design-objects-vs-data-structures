package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class FundCalculator {

    private BillCalculator billCalculator;

    public FundCalculator(BillCalculator billCalculator) {
        this.billCalculator = billCalculator;
    }

    public double getFundBalance(List<Assignment> assignments) {
        return calculateRevenue(assignments) - calculateExpenses(assignments);
    }

    private double calculateRevenue(List<Assignment> assignments) {
        double bill = 0.0;
        for (Assignment assignment : assignments) {
            bill += billCalculator.calculateAssignmentBillPrice(assignment);
        }
        return bill;
    }

    private double calculateExpenses(List<Assignment> assignments) {
        return assignments.stream()
                .map(Assignment::calculateSalaryFundWithBonus)
                .reduce(0.0, Double::sum);
    }

    public void setBillCalculator(BillCalculator billCalculator) {
        this.billCalculator = billCalculator;
    }
}
