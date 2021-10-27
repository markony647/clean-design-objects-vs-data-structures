package com.epam.engx.cleandesign;

import java.util.List;

public class ZoneWithApertures {

    private final List<Zone> apertures;

    public ZoneWithApertures(List<Zone> apertures) {
        this.apertures = apertures;
    }

    public double getArea() {
        return apertures.stream()
                .map(Zone::getArea)
                .reduce(0.0, Double::sum);
    }
}
