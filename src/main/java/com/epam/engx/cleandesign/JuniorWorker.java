package com.epam.engx.cleandesign;

public class JuniorWorker extends Worker {

    public JuniorWorker(double dailyRate, double amountPerDay) {
        super(dailyRate, amountPerDay);
    }

    @Override
    protected double getDailySalary() {
        return getDailyRate();
    }

    @Override
    double getVendorBonus(double initialBonus) {
        return initialBonus;
    }
}
