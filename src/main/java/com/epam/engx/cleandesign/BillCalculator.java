package com.epam.engx.cleandesign;

public class BillCalculator {

    private final MaterialPriceCalculator materialPriceCalculator;
    private final WorkPriceCalculator workPriceCalculator;

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
    }

}
