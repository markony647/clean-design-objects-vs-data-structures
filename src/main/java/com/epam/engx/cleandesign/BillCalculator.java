package com.epam.engx.cleandesign;

import java.util.Map;

public class BillCalculator {

    private static final double MATERIAL_AREA_FACTOR = 10;
    private static final int ONE_DAY_MAX_AREA = 50;
    private static final double MULTI_DAY_PRICE_FACTOR = 1.1;

    private MaterialPriceCalculator materialPriceCalculator;
    WorkPriceCalculator workPriceCalculator;

    public BillCalculator(WorkPriceCalculator workPriceCalculator, MaterialPriceCalculator materialPriceCalculator) {
        this.workPriceCalculator = workPriceCalculator;
        this.materialPriceCalculator = materialPriceCalculator;
    }

    public Double calculateZoneBillPrice(Zone zone) {
        return getZoneBillPrice(zone);
    }

    private Double getZoneBillPrice(Zone zone) {
        double area = zone.getBillableArea();
        return materialPriceCalculator.getPrice(area) + workPriceCalculator.getPrice(area, zone.getType());
//        return getMaterialPrice(area) + getWorkPrice(area, zone.getType());
    }

}
