package com.smalcerz.esperMownit.event;

import java.util.Date;

public class UsageEvent extends AbstractEvent{
	
    private int usage;
 
     
    public UsageEvent(int usage, Date timeOfReading) {
        this.usage = usage;
        this.timeOfReading = timeOfReading;
    }
  
    public int getUsage() {
        return usage;
    }
       
    @Override
    public String toString() {
        return "cpu usage: [" + this.usage + "%]";
    }
}
