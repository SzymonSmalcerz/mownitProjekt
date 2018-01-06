package com.smalcerz.esperMownit.handler.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public abstract class WarningEventSubscriber implements StatementSubscriber {

    /** Logger */
    protected static Logger LOG = LoggerFactory.getLogger(WarningEventSubscriber.class);

}
