package com.epam.engx.cleandesign;

public abstract class Worker {
    private final double dailyRate;
    private final double amountPerDay;
    private boolean isJunior;

    public Worker(double dailyRate, double amountPerDay) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
    }

    public abstract double getDailySalary();

    public abstract double getVendorBonus(double initialBonus);

    public double calculateSalary(double area) {
        return getPaidDays(area) * getDailySalary();
    }

    public boolean isSenior() {
        return !isJunior;
    }

    private int getPaidDays(Double area) {
        return (int) Math.ceil(area / amountPerDay);
    }

    protected double getDailyRate() {
        return dailyRate;
    }

    protected void setJunior(boolean isJunior) {
        this.isJunior = isJunior;
    }

}
