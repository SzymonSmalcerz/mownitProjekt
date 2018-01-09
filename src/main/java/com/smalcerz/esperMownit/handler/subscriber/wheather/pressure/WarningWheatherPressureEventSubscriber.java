package com.smalcerz.esperMownit.handler.subscriber.wheather.pressure;

import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.WheatherPressureEvent;
import com.smalcerz.esperMownit.handler.subscriber.WarningEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class WarningWheatherPressureEventSubscriber extends WarningEventSubscriber {


    public WarningWheatherPressureEventSubscriber(MongoCollection<Document> collection) {
		super(collection);
	}

	/** If 2 consecutive temperature events are greater than this - issue a warning */
    private static final String WARNING_EVENT_THRESHOLD = "60";

    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String warningEventExpression = "select * from WheatherPressureEvent "
                + "match_recognize ( "
                + "       measures A as p1, B as p2 "
                + "       pattern (A B) " 
                + "       define " 
                + "               A as A.pressure > " + WARNING_EVENT_THRESHOLD + ", "
                + "               B as B.pressure > " + WARNING_EVENT_THRESHOLD + ")";
        
        return warningEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */ 
    
    public void update(Map<String, Object> eventMap) {

    	WheatherPressureEvent p1 = (WheatherPressureEvent) eventMap.get("p1");
    	WheatherPressureEvent p2 = (WheatherPressureEvent) eventMap.get("p2");
        
        String actualLog = "\n- [WARNING] : WHEATHER PRESSURE SPIKE DETECTED = " + p1 + "," + p2;
        
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append(actualLog);
        sb.append("\n--------------------------------------------------");
        
        actualLog += ", TIME OF MEASURES: " + p1.getTimeOfReading().toString() + ", " + p2.getTimeOfReading().toString();
        this.saveLogToDatabase(actualLog);
        LOG.debug(sb.toString());

        this.display.append(actualLog);
    }
}
