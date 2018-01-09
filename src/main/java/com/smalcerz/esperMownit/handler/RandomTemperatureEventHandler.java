package com.smalcerz.esperMownit.handler;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.handler.subscriber.wheather.pressure.CriticalWheatherPressureEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.wheather.pressure.MonitorWheatherPressureEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.wheather.pressure.WarningWheatherPressureEventSubscriber;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */

public class RandomTemperatureEventHandler extends EventHandler{
	
	public RandomTemperatureEventHandler(MongoCollection<Document> collection) {
		super(collection);

		// LOG.debug("Configuring..");
	     this.criticalEventSubscriber = new CriticalWheatherPressureEventSubscriber(collection);
	     this.warningEventSubscriber = new WarningWheatherPressureEventSubscriber(collection);
	     this.monitorEventSubscriber = new MonitorWheatherPressureEventSubscriber(collection);

	     initService();
	}

    /**
     * Configure Esper Statement(s).
     */

}
