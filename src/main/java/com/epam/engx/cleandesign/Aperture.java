package com.epam.engx.cleandesign;

public class Aperture {

    private final double height;
    private final double width;

    public Aperture(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getArea() {
        return width * height;
    }
}
