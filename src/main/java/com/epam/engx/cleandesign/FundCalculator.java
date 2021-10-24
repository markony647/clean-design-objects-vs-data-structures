package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class FundCalculator {

    private BillCalculator billCalculator = new BillCalculator();

    public double calculateBalance(List<Assignment> assignments) {
        double salaries = 0.0;
        double bill = 0.0;
        for (Assignment ass : assignments) {
            salaries += ass.calculateSalaryFund();
            bill += summing(ass.getZones(), billCalculator::calculateZoneBillPrice);
        }
        return bill - salaries;
    }

    public void setBillCalculator(BillCalculator billCalculator) {
        this.billCalculator = billCalculator;
    }
}
