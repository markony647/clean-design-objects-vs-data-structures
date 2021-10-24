package com.epam.engx.cleandesign;

import java.util.Map;

public class WorkPriceCalculator {
    private static final int ONE_DAY_MAX_AREA = 50;
    private static final double MULTI_DAY_PRICE_FACTOR = 1.1;

    private final Map<String, Double> zoneTypeWorkPrice;

    public WorkPriceCalculator(Map<String, Double> zoneTypeWorkPrice) {
        this.zoneTypeWorkPrice = zoneTypeWorkPrice;
    }

    public double getPrice(double area, String type) {
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
}
