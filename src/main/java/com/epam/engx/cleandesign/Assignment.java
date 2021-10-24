package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class Assignment {

    private static final double SENIOR_BONUS_FACTOR = 1.5;

    private Worker worker;
    private List<Zone> zones;
    private double vendorBonus;

    public Assignment(Worker worker, List<Zone> zones, double vendorBonus) {
        this.worker = worker;
        this.zones = zones;
        this.vendorBonus = vendorBonus;
    }

    public double calculateSalaryFundWithBonus() {
        double area = getAllZonesBillableArea();
        return worker.calculateSalary(area) + getBonus();
    }

    public void setVendorBonus(double vendorBonus) {
        validateBonus(vendorBonus);
        this.vendorBonus = vendorBonus;
    }

    private double getAllZonesBillableArea() {
        return summing(zones, Zone::getBillableArea);
    }

    private double getBonus() {
        if (worker.hasSeniorityBonus()) {
            return vendorBonus * SENIOR_BONUS_FACTOR;
        } else {
            return vendorBonus;
        }
    }

    private void validateBonus(double vendorBonus) {
        if (vendorBonus < 0) {
            throw new IllegalArgumentException("Vendor bonus should be equal or grater than 0");
        }
    }

    public List<Zone> getZones() {
        return zones;
    }
}
