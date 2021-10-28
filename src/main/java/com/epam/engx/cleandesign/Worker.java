package com.epam.engx.cleandesign;

public abstract class Worker {
    private final double dailyRate;
    private final double amountPerDay;

    public Worker(double dailyRate, double amountPerDay) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
    }

    public abstract double getDailySalary();

    public abstract double getVendorBonus(double initialBonus);

    public double calculateSalary(double area, double vendorBonus) {
        return (getPaidDays(area) * getDailySalary()) + getVendorBonus(vendorBonus);
    }

    protected double getDailyRate() {
        return dailyRate;
    }

    private int getPaidDays(Double area) {
        return (int) Math.ceil(area / amountPerDay);
    }

}
