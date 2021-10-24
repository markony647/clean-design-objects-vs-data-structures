package com.epam.engx.cleandesign;

import java.util.ArrayList;
import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class Zone {

    private final double height;
    private final double width;
    private final String type;
    private final List<Aperture> apertures;

    public Zone(String type, double height, double width) {
        this.height = height;
        this.width = width;
        this.type = type;
        apertures = new ArrayList<>();
    }

    public Zone(String type, double height, double width, List<Aperture> apertures) {
        this.height = height;
        this.width = width;
        this.type = type;
        this.apertures = apertures;
    }

    public double getBillableArea() {
        return getWholeArea() - getNotBillableArea();
    }

    public String getType() {
        return type;
    }

    private double getNotBillableArea() {
        return summing(apertures, Aperture::getArea);
    }

    private double getWholeArea() {
        return height * width;
    }

}
