package com.epam.engx.cleandesign;

public class JuniorWorker extends Worker {

    public JuniorWorker(double dailyRate, double amountPerDay) {
        super(dailyRate, amountPerDay);
    }

    @Override
    public double getDailySalary() {
        return getDailyRate();
    }

    @Override
    public double getVendorBonus(double initialBonus) {
        return initialBonus;
    }
}
