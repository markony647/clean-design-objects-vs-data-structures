package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class Assignment {

    private static final double SENIOR_BONUS_FACTOR = 1.5;

    private Worker worker;
    private List<Zone> zones;
    private double vendorBonus;

    public double calculateSalaryFundWithBonus() {
        double area = getAllZonesBillableArea();
        return worker.calculateSalary(area) + getBonus();
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

    public void setVendorBonus(double vendorBonus) {
        this.vendorBonus = vendorBonus;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}
