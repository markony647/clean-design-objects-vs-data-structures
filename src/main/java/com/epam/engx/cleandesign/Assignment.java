package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class Assignment {

    private static final double SENIOR_BONUS_FACTOR = 1.5;

    private final Worker worker;
    private final List<BillableZone> billableZones;
    private double vendorBonus;

    public Assignment(Worker worker, List<BillableZone> billableZones, double vendorBonus) {
        this.worker = worker;
        this.billableZones = billableZones;
        this.vendorBonus = vendorBonus;
    }

    public double calculateSalaryFund() {
        double area = getAllZonesBillableArea();
        return worker.calculateSalary(area);
    }

    public double getBonus() {
        if (worker.isSenior()) {
            return vendorBonus * SENIOR_BONUS_FACTOR;
        }
        return vendorBonus;
    }

    public void setVendorBonus(double vendorBonus) {
        validateBonus(vendorBonus);
        this.vendorBonus = vendorBonus;
    }

    public List<BillableZone> getZones() {
        return billableZones;
    }

    private double getAllZonesBillableArea() {
        return summing(billableZones, BillableZone::getBillableArea);
    }

    private void validateBonus(double vendorBonus) {
        if (vendorBonus < 0) {
            throw new IllegalArgumentException("Vendor bonus should be equal or grater than 0");
        }
    }
}
