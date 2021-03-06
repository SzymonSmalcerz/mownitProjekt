package com.smalcerz.esperMownit.handler;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.AbstractEvent;
import com.smalcerz.esperMownit.handler.subscriber.EventSubscriber;

public abstract class EventHandler {
	
	/** Logger */
    protected static Logger LOG = LoggerFactory.getLogger(EventHandler.class);
	
	/** Esper service */
	 protected EPServiceProvider epService;
	 protected EPStatement criticalEventStatement;
	 protected EPStatement warningEventStatement;
	 protected EPStatement monitorEventStatement;

	   
	 protected EventSubscriber criticalEventSubscriber;
	 protected EventSubscriber warningEventSubscriber;
	 protected EventSubscriber monitorEventSubscriber;
	 
	 protected MongoCollection<Document> collection;
	 
	 
	 public EventHandler(MongoCollection<Document> collection) {
		 this.collection = collection;
	 }

	/**
	     * EPL to check for a sudden critical rise across 4 events, where the last event is 1.5x greater
	     * than the first event. This is checking for a sudden, sustained escalating rise in the
	     * temperature
	     */
	    
	 public void initService() {

	        LOG.debug("Initializing Servcie ..");
	        Configuration config = new Configuration();
	        config.addEventTypeAutoName("com.smalcerz.esperMownit.event");
	        epService = EPServiceProviderManager.getDefaultProvider(config);

	        createCriticalCheckExpression();
	        createWarningCheckExpression();
	        createMonitorExpression();

	    }
	 
	 protected void createCriticalCheckExpression() {
       criticalEventStatement = epService.getEPAdministrator().createEPL(criticalEventSubscriber.getStatement());
       criticalEventStatement.setSubscriber(criticalEventSubscriber);
   }

   /**
    * EPL to check for 2 consecutive Temperature events over the threshold - if matched, will alert
    * listener.
    */
	 protected void createWarningCheckExpression() {
       warningEventStatement = epService.getEPAdministrator().createEPL(warningEventSubscriber.getStatement());
       warningEventStatement.setSubscriber(warningEventSubscriber);
   }

   /**
    * EPL to monitor the average temperature every 10 seconds. Will call listener on every event.
    */
	 protected void createMonitorExpression() {
       monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventSubscriber.getStatement());
       monitorEventStatement.setSubscriber(monitorEventSubscriber);
   }
	 
	 
	 /**
	     * Handle the incoming Event.
	     */
	  public void handle(AbstractEvent event) {

        epService.getEPRuntime().sendEvent(event);

    }
}
