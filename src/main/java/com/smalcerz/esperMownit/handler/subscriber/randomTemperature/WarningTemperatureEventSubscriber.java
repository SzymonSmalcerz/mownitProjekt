package com.smalcerz.esperMownit.handler.subscriber.randomTemperature;

import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.TemperatureEvent;
import com.smalcerz.esperMownit.handler.subscriber.WarningEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class WarningTemperatureEventSubscriber extends WarningEventSubscriber {


    public WarningTemperatureEventSubscriber(MongoCollection<Document> collection) {
		super(collection);
	}

	/** If 2 consecutive temperature events are greater than this - issue a warning */
    private static final String WARNING_EVENT_THRESHOLD = "65";

    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String warningEventExpression = "select * from TemperatureEvent "
                + "match_recognize ( "
                + "       measures A as temp1, B as temp2 "
                + "       pattern (A B) " 
                + "       define " 
                + "               A as A.temperature > " + WARNING_EVENT_THRESHOLD + ", "
                + "               B as B.temperature > " + WARNING_EVENT_THRESHOLD + ")";
        
        return warningEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */ 
    
    public void update(Map<String, Object> eventMap) {

        // 1st Temperature in the Warning Sequence
        TemperatureEvent temp1 = (TemperatureEvent) eventMap.get("temp1");
        // 2nd Temperature in the Warning Sequence
        TemperatureEvent temp2 = (TemperatureEvent) eventMap.get("temp2");
        
        String actualLog = "\n- [WARNING] : TEMPERATURE SPIKE DETECTED = " + temp1 + "," + temp2;
        
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append(actualLog);
        sb.append("\n--------------------------------------------------");
        
        actualLog += ", TIME OF MEASURES: " + temp1.getTimeOfReading().toString() + ", " + temp2.getTimeOfReading().toString();
        this.saveLogToDatabase(actualLog);
        LOG.debug(sb.toString());

        this.display.append(actualLog);
    }
}
