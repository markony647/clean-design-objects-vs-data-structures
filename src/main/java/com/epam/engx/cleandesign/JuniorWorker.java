package com.epam.engx.cleandesign;

public class JuniorWorker extends Worker {

    public JuniorWorker(double dailyRate, double amountPerDay) {
        super(dailyRate, amountPerDay);
        setJunior(true);
    }

    @Override
    public double getDailySalary() {
        return getDailyRate();
    }
}
