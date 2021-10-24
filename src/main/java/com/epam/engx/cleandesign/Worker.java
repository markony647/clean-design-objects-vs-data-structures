package com.epam.engx.cleandesign;

public class Worker {
    private final double dailyRate;
    private final double amountPerDay;
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

    public double getDailySalary() {
        if (hasSeniorityBonus()) {
            return dailyRate * SENIOR_SALARY_FACTOR;
        }
        return dailyRate;
    }

    public boolean hasSeniorityBonus() {
        return !isJunior;
    }

    public double calculateSalary(double area) {
        return getPaidDays(area) * getDailySalary();
    }

    private int getPaidDays(Double area) {
        return (int) Math.ceil(area / amountPerDay);
    }

}
