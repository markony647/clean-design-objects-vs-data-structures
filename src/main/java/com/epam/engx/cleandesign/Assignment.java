package com.epam.engx.cleandesign;

import java.util.List;

public class Assignment {

    private static final double SENIOR_BONUS_FACTOR = 1.5;

    private Worker worker;
    private List<Zone> zones;
    private double vendorBonus;

    public double getBonus() {
        if (worker.hasSeniorityBonus()) {
            return vendorBonus * SENIOR_BONUS_FACTOR;
        } else {
            return vendorBonus;
        }
    }

    public void setVendorBonus(double vendorBonus) {
        this.vendorBonus = vendorBonus;
    }

    public Worker getWorker() {
        return worker;
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
