package com.epam.engx.cleandesign;

import java.util.List;
import java.util.Map;

import static com.epam.engx.cleandesign.SummingUtil.summing;

public class Assignment {

    private static final double MULTI_DAY_PRICE_FACTOR = 1.1;
    private static final int ONE_DAY_MAX_AREA = 50;

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

    private double calculateWorkPriceForAllZones() {
        double workPrice = 0.0;
        for (BillableZone billableZone : billableZones) {
            workPrice += calculateWorkPriceForZone(billableZone);
        }
        return workPrice;
    }

    private double calculateMaterialPriceForAllZones() {
        return summing(billableZones, BillableZone::getMaterialsPrice);
    }

    private double calculateWorkPriceForZone(BillableZone billableZone) {
        validateType(billableZone.getType());
        return getTotalWorkPrice(billableZone);
    }

    private double getTotalWorkPrice(BillableZone billableZone) {
        double initialPrice = billableZone.getArea() * getSingleUnitPrice(billableZone.getType());
        if (isAreaLessThanDailyCapacity(billableZone.getArea())) {
            return initialPrice;
        }
        return initialPrice * MULTI_DAY_PRICE_FACTOR;
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

    private boolean isAreaLessThanDailyCapacity(double area) {
        return area < ONE_DAY_MAX_AREA;
    }

    public void setVendorBonus(double vendorBonus) {
        validateBonus(vendorBonus);
        this.vendorBonus = vendorBonus;
    }

    private double getAllZonesBillableArea() {
        return summing(billableZones, BillableZone::getArea);
    }

    private void validateBonus(double vendorBonus) {
        if (vendorBonus < 0) {
            throw new IllegalArgumentException("Vendor bonus should be equal or grater than 0");
        }
    }
}
