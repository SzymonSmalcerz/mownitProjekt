package com.smalcerz.esperMownit.handler.subscriber.wheather.pressure;

import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.WheatherPressureEvent;
import com.smalcerz.esperMownit.handler.subscriber.CriticalEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class CriticalWheatherPressureEventSubscriber extends CriticalEventSubscriber {


    public CriticalWheatherPressureEventSubscriber(MongoCollection<Document> collection) {
		super(collection);
	}

	/** Used as the minimum starting threshold for a critical event. */
    private static final String CRITICAL_EVENT_THRESHOLD = "0";
    
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
        String crtiticalEventExpression = "select * from WheatherPressureEvent "
                + "match_recognize ( "
                + "       measures A as p1, B as p2, C as p3, D as p4 "
                + "       pattern (A B C D) " 
                + "       define "
                + "               A as A.pressure > " + CRITICAL_EVENT_THRESHOLD + ", "
                + "               B as (A.pressure < B.pressure), "
                + "               C as (B.pressure < C.pressure), "
                + "               D as (C.pressure < D.pressure) and D.pressure > (A.pressure * " + CRITICAL_EVENT_MULTIPLIER + ")" + ")";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    
 
    public void update(Map<String, Object> eventMap) {
    	
    	
    	WheatherPressureEvent p1 = (WheatherPressureEvent) eventMap.get("p1");
    	WheatherPressureEvent p2 = (WheatherPressureEvent) eventMap.get("p2");
    	WheatherPressureEvent p3 = (WheatherPressureEvent) eventMap.get("p3");
    	WheatherPressureEvent p4 = (WheatherPressureEvent) eventMap.get("p4");
        
        String actualLog = "\n[ALERT] : CRITICAL EVENT DETECTED!" + p1 + " > " + p2 + " > " + p3 + " > " + p4;
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n*" + actualLog );
        sb.append("\n***************************************");
        
        
        actualLog += ", TIME OF MEASURES: " + p1.getTimeOfReading().toString() + ", " +
        									  p2.getTimeOfReading().toString() + ", " +
        									  p3.getTimeOfReading().toString() + ", " +
        									  p4.getTimeOfReading().toString() + ", " ;
        this.saveLogToDatabase(actualLog);
        LOG.debug(sb.toString());
        
        
        this.display.appendCritical(actualLog);
    }

    
}
