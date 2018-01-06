package com.smalcerz.esperMownit.handler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smalcerz.esperMownit.handler.EventHandler;

public abstract class Generator {
	/** Logger */
    protected static Logger LOG = LoggerFactory.getLogger(RandomTemperatureEventGenerator.class);
    
    /** The EventHandler - wraps the Esper engine and processes the Events  */
    protected EventHandler eventHandler;
    
    public abstract void startSendingReadings();
}
