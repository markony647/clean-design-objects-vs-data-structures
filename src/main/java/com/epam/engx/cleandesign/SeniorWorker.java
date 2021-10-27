package com.epam.engx.cleandesign;

public class SeniorWorker extends Worker {

    private static final double SENIOR_SALARY_FACTOR = 1.2;
    private static final double SENIOR_BONUS_FACTOR = 1.5;

    public SeniorWorker(double dailyRate, double amountPerDay) {
        super(dailyRate, amountPerDay);
        setJunior(false);
    }

    @Override
    public double getDailySalary() {
        return getDailyRate() * SENIOR_SALARY_FACTOR;
    }

    @Override
    public double getVendorBonus(double initialBonus) {
        return initialBonus * SENIOR_BONUS_FACTOR;
    }
}
