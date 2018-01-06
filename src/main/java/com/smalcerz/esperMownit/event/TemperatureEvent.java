package com.smalcerz.esperMownit.event;

import java.util.Date;

/**
 * Immutable Temperature Event class. The process control system creates these events. The
 * TemperatureEventHandler picks these up and processes them.
 */
public class TemperatureEvent extends AbstractEvent{

    /** Temperature in Celcius. */
    private int temperature;
    

    public TemperatureEvent(int temperature, Date timeOfReading) {
        this.temperature = temperature;
        this.timeOfReading = timeOfReading;
    }

    /**
     * Get the Temperature.
     * @return Temperature in Celsius
     */
    public int getTemperature() {
        return temperature;
    }


    @Override
    public String toString() {
        return "TemperatureEvent [" + temperature + "C]";
    }

}
