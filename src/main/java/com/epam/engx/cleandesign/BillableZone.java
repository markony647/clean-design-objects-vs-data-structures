package com.epam.engx.cleandesign;

import java.util.Collections;

public class BillableZone {

    private static final double MATERIAL_AREA_FACTOR = 10;
    private static final int ONE_DAY_MAX_AREA = 50;
    private static final double MULTI_DAY_PRICE_FACTOR = 1.1;

    private final String type;
    private final ZoneWithApertures zoneWithApertures;

    public BillableZone(String type, Zone wholeZone) {
        this.type = type;
        this.zoneWithApertures = new ZoneWithApertures(wholeZone, Collections.emptyList());
    }

    public BillableZone(String type, ZoneWithApertures zoneWithApertures) {
        this.type = type;
        this.zoneWithApertures = zoneWithApertures;
    }

    public double getArea() {
        return zoneWithApertures.getArea();
    }

    public double calculateZoneBillPrice(double workUnitPrice) {
        return getMaterialsPrice() + getWorkPrice(workUnitPrice);
    }

    public String getType() {
        return type;
    }

    private double getMaterialsPrice() {
        return getArea() * MATERIAL_AREA_FACTOR;
    }

    private double getWorkPrice(double workUnitPrice) {
        double initialWorkPrice =  workUnitPrice * getArea();
        if (isAreaLessThanDailyCapacity()) {
            return initialWorkPrice;
        }
        return initialWorkPrice * MULTI_DAY_PRICE_FACTOR;
    }

    private boolean isAreaLessThanDailyCapacity() {
        return getArea() < ONE_DAY_MAX_AREA;
    }

}
