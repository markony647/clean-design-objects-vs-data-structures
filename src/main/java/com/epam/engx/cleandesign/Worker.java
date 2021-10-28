package com.epam.engx.cleandesign;

public abstract class Worker {
    private final double dailyRate;
    private final double amountPerDay;

    public Worker(double dailyRate, double amountPerDay) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
    }

    public double calculateSalaryWithBonus(double area, double vendorBonus) {
        return getSalaryForArea(area) + getVendorBonus(vendorBonus);
    }

    abstract double getDailySalary();

    abstract double getVendorBonus(double initialBonus);

    protected double getDailyRate() {
        return dailyRate;
    }

    private double getSalaryForArea(double area) {
        return getPaidDays(area) * getDailySalary();
    }

    private int getPaidDays(Double area) {
        return (int) Math.ceil(area / amountPerDay);
    }

}
