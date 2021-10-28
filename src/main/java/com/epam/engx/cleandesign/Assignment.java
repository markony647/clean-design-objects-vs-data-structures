package com.epam.engx.cleandesign;

import java.util.List;
import java.util.Map;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

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
        return (calculateMaterialPriceForAllZones() + calculateWorkPriceForAllZones()) - calculateSalaryFund();
    }

    private double calculateSalaryFund() {
        double area = getAllZonesBillableArea();
        return worker.calculateSalaryWithBonus(area, vendorBonus);
    }

    private double calculateWorkPriceForAllZones() {
        double workPrice = 0.0;
        for (BillableZone billableZone : billableZones) {
            double billableArea = billableZone.getBillableArea();
            workPrice += calculateWorkPriceForZone(billableArea, billableZone.getType());
        }
        return workPrice;
    }

    private double calculateMaterialPriceForAllZones() {
        double materialPrice = 0.0;
        for (BillableZone billableZone : billableZones) {
            materialPrice += billableZone.getMaterialsPrice();
        }
        return materialPrice;
    }

    private double calculateWorkPriceForZone(double area, String type) {
        validateType(type);
        return getTotalPrice(area, type);
    }

    private double getTotalPrice(double area, String type) {
        double initialPrice = area * getSingleUnitPrice(type);
        if (isAreaLessThanDailyCapacity(area)) {
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
        return zoneTypeWorkPrice.get(type);
    }

    private boolean isAreaLessThanDailyCapacity(double area) {
        return area < ONE_DAY_MAX_AREA;
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
