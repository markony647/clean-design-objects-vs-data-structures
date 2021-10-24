package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class FundCalculator {

    private BillCalculator billCalculator = new BillCalculator();

    public double getFundBalance(List<Assignment> assignments) {
        double salaryExpenses = calculateExpenses(assignments);
        double revenue = calculateRevenue(assignments);
        return revenue - salaryExpenses;
    }

    private double calculateRevenue(List<Assignment> assignments) {
        double bill = 0.0;
        for (Assignment assignment : assignments) {
            List<Zone> zones = assignment.getZones();
            bill += summing(zones, billCalculator::calculateZoneBillPrice);
        }
        return bill;
    }

    private double calculateExpenses(List<Assignment> assignments) {
        double salary = 0.0;
        for (Assignment assignment : assignments) {
            double area = summing(assignment.getZones(), Zone::getBillableArea);
            salary += assignment.calculateSalaryFund(area) + assignment.getBonus();
        }
        return salary;
    }

    public void setBillCalculator(BillCalculator billCalculator) {
        this.billCalculator = billCalculator;
    }
}
