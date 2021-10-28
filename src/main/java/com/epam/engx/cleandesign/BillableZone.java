package com.epam.engx.cleandesign;

import java.util.Collections;

public class BillableZone {

    private static final double MATERIAL_AREA_FACTOR = 10;

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

    public double getBillableArea() {
        return getWholeArea() - getAperturesArea();
    }

    public double getMaterialsPrice() {
        return getBillableArea() * MATERIAL_AREA_FACTOR;
    }

    public String getType() {
        return type;
    }

    private double getAperturesArea() {
        return zoneWithApertures.getArea();
    }

    private double getWholeArea() {
        return zone.getArea();
    }

}
