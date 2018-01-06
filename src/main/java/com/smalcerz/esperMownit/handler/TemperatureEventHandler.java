package com.smalcerz.esperMownit.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.smalcerz.esperMownit.event.TemperatureEvent;
import com.smalcerz.esperMownit.handler.subscriber.CriticalEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.MonitorEventSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.StatementSubscriber;
import com.smalcerz.esperMownit.handler.subscriber.WarningEventSubscriber;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */

public class TemperatureEventHandler{
	
	private static TemperatureEventHandler instance;
	
	
	
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TemperatureEventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement criticalEventStatement;
    private EPStatement warningEventStatement;
    private EPStatement monitorEventStatement;

   
    private StatementSubscriber criticalEventSubscriber;
    private StatementSubscriber warningEventSubscriber;
    private StatementSubscriber monitorEventSubscriber;

    /**
     * Configure Esper Statement(s).
     */
    
    private TemperatureEventHandler() {
		// LOG.debug("Configuring..");
	     this.criticalEventSubscriber = new CriticalEventSubscriber();
	     this.warningEventSubscriber = new WarningEventSubscriber();
	     this.monitorEventSubscriber = new MonitorEventSubscriber();

	     initService();
	}
    
    public void initService() {

        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.smalcerz.esperMownit.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createCriticalTemperatureCheckExpression();
        createWarningTemperatureCheckExpression();
        createTemperatureMonitorExpression();

    }

    /**
     * EPL to check for a sudden critical rise across 4 events, where the last event is 1.5x greater
     * than the first event. This is checking for a sudden, sustained escalating rise in the
     * temperature
     */
    private void createCriticalTemperatureCheckExpression() {
        
        LOG.debug("create Critical Temperature Check Expression");
        criticalEventStatement = epService.getEPAdministrator().createEPL(criticalEventSubscriber.getStatement());
        criticalEventStatement.setSubscriber(criticalEventSubscriber);
    }

    /**
     * EPL to check for 2 consecutive Temperature events over the threshold - if matched, will alert
     * listener.
     */
    private void createWarningTemperatureCheckExpression() {

        LOG.debug("create Warning Temperature Check Expression");
        warningEventStatement = epService.getEPAdministrator().createEPL(warningEventSubscriber.getStatement());
        warningEventStatement.setSubscriber(warningEventSubscriber);
    }

    /**
     * EPL to monitor the average temperature every 10 seconds. Will call listener on every event.
     */
    private void createTemperatureMonitorExpression() {

        LOG.debug("create Timed Average Monitor");
        monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventSubscriber.getStatement());
        monitorEventStatement.setSubscriber(monitorEventSubscriber);
    }

    /**
     * Handle the incoming TemperatureEvent.
     */
    public void handle(TemperatureEvent event) {

        LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

    
    public static synchronized TemperatureEventHandler getInstance() {
    	if(instance == null) {
    		instance = new TemperatureEventHandler();
    	}
    	return instance;
    }
}
