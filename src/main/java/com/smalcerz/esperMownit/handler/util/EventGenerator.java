package com.smalcerz.esperMownit.handler.util;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.handler.EventHandler;

public abstract class EventGenerator {
	/** Logger */
    protected static Logger LOG = LoggerFactory.getLogger(RandomTemperatureEventGenerator.class);
    
    /** The EventHandler - wraps the Esper engine and processes the Events  */
    protected EventHandler eventHandler;
    
    /** Connection to database*/
    protected MongoCollection<Document> collection;
    
    public EventGenerator(MongoCollection<Document> collection) {
    	this.collection = collection;
    }
    
    public abstract void startSendingReadings();
}
