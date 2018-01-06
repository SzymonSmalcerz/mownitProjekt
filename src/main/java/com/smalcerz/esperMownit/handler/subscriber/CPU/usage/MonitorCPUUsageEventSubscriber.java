package com.smalcerz.esperMownit.handler.subscriber.CPU.usage;

import java.util.Map;

import com.smalcerz.esperMownit.handler.subscriber.MonitorEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class MonitorCPUUsageEventSubscriber extends MonitorEventSubscriber {

  

    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return "select avg(usage) as avg_val from UsageEvent.win:time_batch(5 sec)";
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {

        // average temp over 10 secs
        Double avg = (Double) eventMap.get("avg_val");

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [MONITOR] Average cpu usage = " + avg + "%");
        sb.append("\n---------------------------------");

        LOG.debug(sb.toString());
    }
}
