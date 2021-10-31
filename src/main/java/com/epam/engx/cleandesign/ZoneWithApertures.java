package com.epam.engx.cleandesign;

import java.util.List;

public class ZoneWithApertures extends Zone {

    private final List<Zone> apertures;
    private final Zone wholeZone;

    public ZoneWithApertures(Zone wholeZone, List<Zone> apertures) {
        super(wholeZone.getHeight(), wholeZone.getWidth());
        this.wholeZone = wholeZone;
        this.apertures = apertures;
    }

    @Override
    public double getArea() {
        return wholeZone.getArea() - getAperturesArea();
    }

    public double getAperturesArea() {
        return apertures.stream()
                .map(Zone::getArea)
                .reduce(0.0, Double::sum);
    }
}
