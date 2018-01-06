package com.smalcerz.esperMownit.handler.subscriber.randomTemperature;

import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.TemperatureEvent;
import com.smalcerz.esperMownit.handler.subscriber.CriticalEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class CriticalTemperatureEventSubscriber extends CriticalEventSubscriber {


    public CriticalTemperatureEventSubscriber(MongoCollection<Document> collection) {
		super(collection);
	}

	/** Used as the minimum starting threshold for a critical event. */
    private static final String CRITICAL_EVENT_THRESHOLD = "0";
    
    /**
     * If the last event in a critical sequence is this much greater than the first - issue a
     * critical alert.
     */
    private static final String CRITICAL_EVENT_MULTIPLIER = "1";
    
    /**
     * {@inheritDoc}
     */
    
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "select * from TemperatureEvent "
                + "match_recognize ( "
                + "       measures A as temp1, B as temp2, C as temp3, D as temp4 "
                + "       pattern (A B C D) " 
                + "       define "
                + "               A as A.temperature > " + CRITICAL_EVENT_THRESHOLD + ", "
                + "               B as (A.temperature < B.temperature), "
                + "               C as (B.temperature < C.temperature), "
                + "               D as (C.temperature < D.temperature) and D.temperature > (A.temperature * " + CRITICAL_EVENT_MULTIPLIER + ")" + ")";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    
 
    public void update(Map<String, Object> eventMap) {
    	
    	// 1st Temperature in the Critical Sequence
        TemperatureEvent temp1 = (TemperatureEvent) eventMap.get("temp1");
        // 2nd Temperature in the Critical Sequence
        TemperatureEvent temp2 = (TemperatureEvent) eventMap.get("temp2");
        // 3rd Temperature in the Critical Sequence
        TemperatureEvent temp3 = (TemperatureEvent) eventMap.get("temp3");
        // 4th Temperature in the Critical Sequence
        TemperatureEvent temp4 = (TemperatureEvent) eventMap.get("temp4");
        
        String actualLog = "\n[ALERT] : CRITICAL EVENT DETECTED!" + temp1 + " > " + temp2 + " > " + temp3 + " > " + temp4;
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n*" + actualLog );
        sb.append("\n***************************************");
        
        
        actualLog += ", TIME OF MEASURES: " + temp1.getTimeOfReading().toString() + ", " +
        									  temp2.getTimeOfReading().toString() + ", " +
        									  temp3.getTimeOfReading().toString() + ", " +
        									  temp4.getTimeOfReading().toString() + ", " ;
        this.saveLogToDatabase(actualLog);
        LOG.debug(sb.toString());
        
        
        this.display.appendCritical(actualLog);
    }

    
}
