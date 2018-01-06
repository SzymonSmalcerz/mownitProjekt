package com.smalcerz.esperMownit.handler;

import com.smalcerz.esperMownit.handler.subscriber.CPU.usage.CriticalCPUUsageEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.CPU.usage.MonitorCPUUsageEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.CPU.usage.WarningCPUUsageEventSubscriber;

public class CPUUsageEventHandler extends EventHandler {
	private static CPUUsageEventHandler instance;

    /**
     * Configure Esper Statement(s).
     */
    
    private CPUUsageEventHandler() {
		// LOG.debug("Configuring..");
	     this.criticalEventSubscriber = new CriticalCPUUsageEventSubscriber();
	     this.warningEventSubscriber = new WarningCPUUsageEventSubscriber();
	     this.monitorEventSubscriber = new MonitorCPUUsageEventSubscriber();

	     initService();
	}
    
   

    public static synchronized CPUUsageEventHandler getInstance() {
    	if(instance == null) {
    		instance = new CPUUsageEventHandler();
    	}
    	return instance;
    }
}
