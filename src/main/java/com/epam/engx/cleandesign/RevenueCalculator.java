package com.epam.engx.cleandesign;

public class RevenueCalculator {

    private final MaterialPriceCalculator materialPriceCalculator;
    private final WorkPriceCalculator workPriceCalculator;

    public RevenueCalculator(WorkPriceCalculator workPriceCalculator, MaterialPriceCalculator materialPriceCalculator) {
        this.workPriceCalculator = workPriceCalculator;
        this.materialPriceCalculator = materialPriceCalculator;
    }

    public Double calculate(Assignment assignment) {
        return assignment.getZones().stream()
                .map(this::getZoneBillPrice)
                .reduce(0.0, Double::sum);
    }

    private Double getZoneBillPrice(BillableZone billableZone) {
        double area = billableZone.getBillableArea();
        return materialPriceCalculator.calculate(area) + workPriceCalculator.calculate(area, billableZone.getType());
    }

}
