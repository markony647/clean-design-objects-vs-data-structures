package com.epam.engx.cleandesign;

import java.util.List;
import java.util.Map;

public class Assignment {

    private final Worker worker;
    private final List<BillableZone> billableZones;
    private final Map<String, Double> zoneTypeWorkPrice;
    private double vendorBonus;

    public Assignment(Worker worker, List<BillableZone> billableZones, Map<String, Double> zoneTypeWorkPrice, double vendorBonus) {
        this.worker = worker;
        this.billableZones = billableZones;
        this.zoneTypeWorkPrice = zoneTypeWorkPrice;
        this.vendorBonus = vendorBonus;
    }

    public double calculateFundBalance() {
        return calculateWorkAndMaterialPriceForAllZones() - calculateSalaryFund();
    }

    public void setVendorBonus(double vendorBonus) {
        validateBonus(vendorBonus);
        this.vendorBonus = vendorBonus;
    }

    private double calculateSalaryFund() {
        double area = getAllZonesBillableArea();
        return worker.calculateSalaryWithBonus(area, vendorBonus);
    }

    private double calculateWorkAndMaterialPriceForAllZones() {
        double price = 0.0;
        for (BillableZone billableZone : billableZones) {
            double workPrice = getSingleUnitPrice(billableZone.getType());
            price += billableZone.calculateZoneBillPrice(workPrice);
        }
        return price;
    }

    private void validateType(String type) {
        if (isNotContainsWorkType(type))
            throw new WrongZoneTypeException();
    }

    private boolean isNotContainsWorkType(String type) {
        return !zoneTypeWorkPrice.containsKey(type);
    }

    private double getSingleUnitPrice(String type) {
        validateType(type);
        return zoneTypeWorkPrice.get(type);
    }

    private double getAllZonesBillableArea() {
        return billableZones.stream()
                .map(BillableZone::getArea)
                .reduce(0.0, Double::sum);
    }

    private void validateBonus(double vendorBonus) {
        if (vendorBonus < 0) {
            throw new IllegalArgumentException("Vendor bonus should be equal or grater than 0");
        }
    }
}
