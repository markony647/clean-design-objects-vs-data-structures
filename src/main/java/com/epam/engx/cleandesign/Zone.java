package com.epam.engx.cleandesign;

public class Zone {

    private final double height;
    private final double width;

    public Zone(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getArea() {
        return width * height;
    }

}
