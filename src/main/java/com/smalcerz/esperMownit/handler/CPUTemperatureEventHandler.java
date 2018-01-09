package com.smalcerz.esperMownit.handler;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.handler.subscriber.CPU.temperature.*;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */

public class CPUTemperatureEventHandler extends EventHandler{
	
	public CPUTemperatureEventHandler(MongoCollection<Document> collection) {
		super(collection);

		// LOG.debug("Configuring..");
	     this.criticalEventSubscriber = new CriticalCPUTemperatureEventSubscriber(collection);
	     this.warningEventSubscriber = new WarningCPUTemperatureEventSubscriber(collection);
	     this.monitorEventSubscriber = new MonitorCPUTemperatureEventSubscriber(collection);

	     initService();
	}

    /**
     * Configure Esper Statement(s).
     */

}
