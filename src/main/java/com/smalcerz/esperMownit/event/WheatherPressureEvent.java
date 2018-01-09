package com.smalcerz.esperMownit.event;

import java.util.Date;

public class WheatherPressureEvent extends AbstractEvent {
	
	private int pressure;
	 
    
    public WheatherPressureEvent(int pressure, Date timeOfReading) {
        this.pressure = pressure;
        this.timeOfReading = timeOfReading;
    }
  
    public int getPressure() {
        return pressure;
    }
       
    @Override
    public String toString() {
        return "wheather pressure: [" + this.pressure + "%]";
    }
}
