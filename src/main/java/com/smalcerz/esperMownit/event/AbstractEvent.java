package com.smalcerz.esperMownit.event;

import java.util.Date;

public abstract class AbstractEvent {

    
    protected Date timeOfReading;
    
    public Date getTimeOfReading() {
        return timeOfReading;
    }

}
