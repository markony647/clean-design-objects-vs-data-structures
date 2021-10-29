package com.epam.engx.cleandesign;

import java.util.Collections;

public class BillableZone {

    private static final double MATERIAL_AREA_FACTOR = 10;
    private static final int ONE_DAY_MAX_AREA = 50;
    private static final double MULTI_DAY_PRICE_FACTOR = 1.1;

    private final String type;
    private final Zone zone;
    private ZoneWithApertures zoneWithApertures;

    public BillableZone(String type, Zone wholeZone) {
        this.type = type;
        this.zone = wholeZone;
        this.zoneWithApertures = new ZoneWithApertures(Collections.emptyList());
    }

    public BillableZone(String type, Zone wholeZone, ZoneWithApertures zoneWithApertures) {
        this(type, wholeZone);
        this.zoneWithApertures = zoneWithApertures;
    }

    public double getArea() {
        return getWholeArea() - getAperturesArea();
    }

    public double getMaterialsPrice() {
        return getArea() * MATERIAL_AREA_FACTOR;
    }

    public String getType() {
        return type;
    }

    public double calculateZoneBillPrice(double workUnitPrice) {
        return getMaterialsPrice() + getWorkPrice(workUnitPrice);
    }

    private double getWorkPrice(double workUnitPrice) {
        double initialWorkPrice =  workUnitPrice * getArea();
        if (isAreaLessThanDailyCapacity()) {
            return initialWorkPrice;
        }
        return initialWorkPrice * MULTI_DAY_PRICE_FACTOR;
    }

    private double getAperturesArea() {
        return zoneWithApertures.getArea();
    }

    private double getWholeArea() {
        return zone.getArea();
    }

    private boolean isAreaLessThanDailyCapacity() {
        return getArea() < ONE_DAY_MAX_AREA;
    }

}
