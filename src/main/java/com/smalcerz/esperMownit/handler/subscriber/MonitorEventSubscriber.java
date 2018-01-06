package com.smalcerz.esperMownit.handler.subscriber;

import org.bson.Document;
import com.mongodb.client.MongoCollection;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public abstract class MonitorEventSubscriber extends EventSubscriber {

    public MonitorEventSubscriber(MongoCollection<Document> collection) {
    	super(collection);
    }
   
}
