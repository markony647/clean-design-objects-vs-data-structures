package com.epam.engx.cleandesign;

import java.util.ArrayList;
import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class Zone {

    private final double height;
    private final double width;
    private final String type;
    private List<Aperture> apertures = new ArrayList<>();

    public Zone(String type, double height, double width) {
        this.height = height;
        this.width = width;
        this.type = type;
    }

    public double getBillableArea() {
        return getWholeArea() - getNotBillableArea();
    }

    private double getNotBillableArea() {
        return summing(apertures, Aperture::getArea);
    }

    private double getWholeArea() {
        return height * width;
    }

    public void setApertures(List<Aperture> apertures) {
        this.apertures = apertures;
    }

    public String getType() {
        return type;
    }

}
