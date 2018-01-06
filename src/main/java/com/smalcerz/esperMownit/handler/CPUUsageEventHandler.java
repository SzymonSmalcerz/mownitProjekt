package com.smalcerz.esperMownit.handler;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.handler.subscriber.CPU.usage.CriticalCPUUsageEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.CPU.usage.MonitorCPUUsageEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.CPU.usage.WarningCPUUsageEventSubscriber;

public class CPUUsageEventHandler extends EventHandler {

    /**
     * Configure Esper Statement(s).
     */
    
    public  CPUUsageEventHandler(MongoCollection<Document> collection) {
		super(collection);
		
	     this.criticalEventSubscriber = new CriticalCPUUsageEventSubscriber(collection);
	     this.warningEventSubscriber = new WarningCPUUsageEventSubscriber(collection);
	     this.monitorEventSubscriber = new MonitorCPUUsageEventSubscriber(collection);
	     this.collection.insertOne(new Document("log", "inserted in constructor of eventhandler"));
			
	     initService();
	     
	}
    
}
