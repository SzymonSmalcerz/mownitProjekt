package com.smalcerz.esperMownit.handler.subscriber.randomTemperature;

import java.util.Map;

import com.smalcerz.esperMownit.event.TemperatureEvent;
import com.smalcerz.esperMownit.handler.subscriber.WarningEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class WarningTemperatureEventSubscriber extends WarningEventSubscriber {


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

        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- [WARNING] : TEMPERATURE SPIKE DETECTED = " + temp1 + "," + temp2);
        sb.append("\n--------------------------------------------------");

        LOG.debug(sb.toString());
    }
}
