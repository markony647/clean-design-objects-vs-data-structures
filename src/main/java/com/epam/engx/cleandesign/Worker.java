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

    public double getSalaryFor(int days) {
        double baseSalary = getBaseSalaryFor(days);
        if (isJunior) {
            return baseSalary;
        }
        return baseSalary * SENIOR_SALARY_FACTOR;
    }

    private double getBaseSalaryFor(int days) {
        return dailyRate * days;
    }

    public boolean isJunior() {
        return isJunior;
    }

}
