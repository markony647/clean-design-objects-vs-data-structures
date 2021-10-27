package com.epam.engx.cleandesign;

import java.util.Collections;

public class BillableZone {

    private final double height;
    private final double width;
    private final String type;
    private ZoneWithApertures zoneWithApertures;

    public BillableZone(String type, double height, double width) {
        this.height = height;
        this.width = width;
        this.type = type;
        this.zoneWithApertures = new ZoneWithApertures(Collections.emptyList());
    }

    public BillableZone(String type, double height, double width, ZoneWithApertures zoneWithApertures) {
        this(type, height, width);
        this.zoneWithApertures = zoneWithApertures;
    }

    public double getBillableArea() {
        return getWholeArea() - getNotBillableArea();
    }

    public String getType() {
        return type;
    }

    private double getNotBillableArea() {
        return zoneWithApertures.getArea();
    }

    private double getWholeArea() {
        return height * width;
    }

}
