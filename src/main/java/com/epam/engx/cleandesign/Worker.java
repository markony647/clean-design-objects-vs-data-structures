package com.epam.engx.cleandesign;

public class Worker {
    private double dailyRate;
    private double amountPerDay;
    private boolean isJunior = false;

    private static final double SENIOR_SALARY_FACTOR = 1.2;

    public Worker(double dailyRate, double amountPerDay) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
    }

    public Worker(double dailyRate, double amountPerDay, boolean isJunior) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
        this.isJunior = isJunior;
    }

    public int getPaidDays(Double area) {
        return (int) Math.ceil(area / amountPerDay);
    }

    public double getDailySalary() {
        if (isJunior) {
            return dailyRate;
        }
        return dailyRate * SENIOR_SALARY_FACTOR;
    }

    public boolean isJunior() {
        return isJunior;
    }

    public double calculateSalary(double area) {
        return getPaidDays(area) * getDailySalary();
    }
}
