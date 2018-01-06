package com.smalcerz.esperMownit.handler;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.handler.subscriber.randomTemperature.CriticalTemperatureEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.randomTemperature.MonitorTemperatureEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.randomTemperature.WarningTemperatureEventSubscriber;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */

public class TemperatureEventHandler extends EventHandler{
	
	public TemperatureEventHandler(MongoCollection<Document> collection) {
		super(collection);

		// LOG.debug("Configuring..");
	     this.criticalEventSubscriber = new CriticalTemperatureEventSubscriber(collection);
	     this.warningEventSubscriber = new WarningTemperatureEventSubscriber(collection);
	     this.monitorEventSubscriber = new MonitorTemperatureEventSubscriber(collection);

	     initService();
	}

    /**
     * Configure Esper Statement(s).
     */

}
