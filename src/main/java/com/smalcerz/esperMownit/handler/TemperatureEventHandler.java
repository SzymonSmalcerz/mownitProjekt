package com.smalcerz.esperMownit.handler;

import com.smalcerz.esperMownit.handler.subscriber.randomTemperature.CriticalTemperatureEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.randomTemperature.MonitorTemperatureEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.randomTemperature.WarningTemperatureEventSubscriber;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */

public class TemperatureEventHandler extends EventHandler{
	
	private static TemperatureEventHandler instance;

    /**
     * Configure Esper Statement(s).
     */
    
    private TemperatureEventHandler() {
		// LOG.debug("Configuring..");
	     this.criticalEventSubscriber = new CriticalTemperatureEventSubscriber();
	     this.warningEventSubscriber = new WarningTemperatureEventSubscriber();
	     this.monitorEventSubscriber = new MonitorTemperatureEventSubscriber();

	     initService();
	}
    
   

    public static synchronized TemperatureEventHandler getInstance() {
    	if(instance == null) {
    		instance = new TemperatureEventHandler();
    	}
    	return instance;
    }
}
