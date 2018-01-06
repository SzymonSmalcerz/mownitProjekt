package com.smalcerz.esperMownit.handler.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public abstract class MonitorEventSubscriber implements StatementSubscriber {

    /** Logger */
    protected static Logger LOG = LoggerFactory.getLogger(MonitorEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
   

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public abstract void update(Map<String, Double> eventMap);
}
