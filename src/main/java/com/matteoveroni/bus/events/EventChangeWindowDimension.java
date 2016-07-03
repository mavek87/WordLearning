package com.matteoveroni.bus.events;

/**
 *
 * @author Matteo Veroni
 */
public class EventChangeWindowDimension {

    private final double width;
    private final double height;

    public EventChangeWindowDimension(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
