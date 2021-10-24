package com.epam.engx.cleandesign;

public class MaterialPriceCalculator {
    private static final double MATERIAL_AREA_FACTOR = 10;

    public double calculate(double area) {
        return area * MATERIAL_AREA_FACTOR;
    }
}
