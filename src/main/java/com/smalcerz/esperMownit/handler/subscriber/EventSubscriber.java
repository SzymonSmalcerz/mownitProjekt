package com.smalcerz.esperMownit.handler.subscriber;

import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;

public abstract class EventSubscriber {
	
	
	
    /** Logger */
    protected static Logger LOG = LoggerFactory.getLogger(EventSubscriber.class);

     
    protected MongoCollection<Document> collection;
	public EventSubscriber(MongoCollection<Document> collection) {
		this.collection = collection;
	}


	/**
     * Get the EPL Stamement the Subscriber will listen to.
     * @return EPL Statement
     */
    public abstract String getStatement();

    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public abstract void update(Map<String, Object> eventMap);
    
    
    protected void saveLogToDatabase(String log) {
    	System.out.println("IM SAVINGG");
   	 Document document = new Document("log", log)
   			 				.append("type", "database");
     this.collection.insertOne(document);
     System.out.println("IM SAVINGG222");
     
    }
}
