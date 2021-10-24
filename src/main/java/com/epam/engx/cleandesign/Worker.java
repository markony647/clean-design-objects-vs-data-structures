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

    public int getPaidDays(Double area) {
        return (int) Math.ceil(area / amountPerDay);
    }

    public double calculateSalary(Double area) {
        return getPaidDays(area) * calculateSalary();
    }

    public double calculateSalary() {
        double baseSalary = getBaseSalary();
        if (hasSeniorityBonus()) {
            return baseSalary * SENIOR_SALARY_FACTOR;
        }
        return baseSalary;
    }

    public boolean hasSeniorityBonus() {
        return !isJunior;
    }

    private double getBaseSalary() {
        return dailyRate;
    }

    public boolean isJunior() {
        return isJunior;
    }

}
