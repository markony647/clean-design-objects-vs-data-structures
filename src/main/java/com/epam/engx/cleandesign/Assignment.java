package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class Assignment {
    private static final double SENIOR_BONUS_FACTOR = 1.5;

    private Worker worker;
    private List<Zone> zones;
    private double vendorBonus;

    public double calculateSalaryFund() {
        return worker.calculateSalary(getTotalArea()) + getAssignmentBonus();
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

    private double getTotalArea() {
        return summing(zones, Zone::getBillableArea);
    }

    private Double getAssignmentBonus() {
        if (worker.hasSeniorityBonus()) {
            return vendorBonus;
        }
        return vendorBonus * SENIOR_BONUS_FACTOR;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}
