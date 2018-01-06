package com.smalcerz.esperMownit.handler.subscriber.CPU.usage;

import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.UsageEvent;
import com.smalcerz.esperMownit.handler.subscriber.CriticalEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class CriticalCPUUsageEventSubscriber extends CriticalEventSubscriber {


    public CriticalCPUUsageEventSubscriber(MongoCollection<Document> collection) {
		super(collection);
		this.collection.insertOne(new Document("log", "inserted in constructor of eventsubscriber"));
		
	}

	/** Used as the minimum starting threshold for a critical event. */
    private static final String CRITICAL_EVENT_THRESHOLD = "90";
    
    /**
     * If the last event in a critical sequence is this much greater than the first - issue a
     * critical alert.
     */
    private static final String CRITICAL_EVENT_MULTIPLIER = "1.0";
    
    /**
     * {@inheritDoc}
     */
    
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "select * from UsageEvent "
                + "match_recognize ( "
                + "       measures A as usage1, B as usage2, C as usage3, D as usage4 "
                + "       pattern (A B C D) " 
                + "       define "
                + "               A as A.usage > " + CRITICAL_EVENT_THRESHOLD + ", "
                + "               B as (A.usage < B.usage), "
                + "               C as (B.usage < C.usage), "
                + "               D as (C.usage < D.usage) and D.usage > (A.usage * " + CRITICAL_EVENT_MULTIPLIER + ")" + ")";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
     
 
    public void update(Map<String, Object> eventMap) {
    	
    	System.out.println("KUUUUUURRRRRRRRWWWWWWAAAAAAAAAAAAAAAAAA");
    	UsageEvent usage1 = (UsageEvent) eventMap.get("usage1");
    	UsageEvent usage2 = (UsageEvent) eventMap.get("usage2");
    	UsageEvent usage3 = (UsageEvent) eventMap.get("usage3");
    	UsageEvent usage4 = (UsageEvent) eventMap.get("usage4");
    	
    	String actualLog = "\n[ALERT] : CRITICAL EVENT DETECTED! :" + usage1 + " > " + usage2 + " > " + usage3 + " > " + usage4 ;
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* "+ actualLog);
        sb.append("\n***************************************");
        
        actualLog += ", TIME OF MEASURES: " + usage1.getTimeOfReading().toString() + ", " +
        									  usage2.getTimeOfReading().toString() + ", " +
        									  usage3.getTimeOfReading().toString() + ", " +
        									  usage4.getTimeOfReading().toString() + ", " ;
        
        this.saveLogToDatabase(actualLog);
        LOG.debug(sb.toString());
        this.display.appendCritical(actualLog);
    }

    
}
