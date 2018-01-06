package com.smalcerz.esperMownit.handler.subscriber.CPU.usage;

import java.util.Map;

import com.smalcerz.esperMownit.event.UsageEvent;
import com.smalcerz.esperMownit.handler.subscriber.WarningEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class WarningCPUUsageEventSubscriber extends WarningEventSubscriber {


    /** If 2 consecutive temperature events are greater than this - issue a warning */
    private static final String WARNING_EVENT_THRESHOLD = "50";

    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String warningEventExpression = "select * from UsageEvent "
                + "match_recognize ( "
                + "       measures A as usage1, B as usage2 "
                + "       pattern (A B) " 
                + "       define " 
                + "               A as A.usage > " + WARNING_EVENT_THRESHOLD + ", "
                + "               B as B.usage > " + WARNING_EVENT_THRESHOLD + ")";
        
        return warningEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    
    public void update(Map<String, Object> eventMap) {

    	UsageEvent usage1 = (UsageEvent) eventMap.get("usage1");
    	UsageEvent usage2 = (UsageEvent) eventMap.get("usage2");

        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- [WARNING] : CPU USAGE SPIKE DETECTED = " + usage1 + ", " + usage2);
        sb.append("\n--------------------------------------------------");

        LOG.debug(sb.toString());
    }
}
