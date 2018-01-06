package com.smalcerz.esperMownit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smalcerz.esperMownit.handler.util.CpuTemperatureEventGenerator;
import com.smalcerz.esperMownit.handler.util.RandomTemperatureEventGenerator;



/**
 * Entry point for the Demo. Run this from your IDE, or from the command line using 'mvn exec:java'.
 */


public class Main {
	/** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Main.class);

    
    /**
     * Main method - start the Demo!
     */
    public static void main(String[] args) throws Exception {

        LOG.debug("Starting...");


//        // Load spring config
//        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" });
//        BeanFactory factory = (BeanFactory) appContext;
//
//        // Start Demo
//        RandomTemperatureEventGenerator generator = (RandomTemperatureEventGenerator) factory.getBean("eventGenerator");
//      RandomTemperatureEventGenerator generator = new RandomTemperatureEventGenerator();
//      generator.startSendingReadings();
        
        CpuTemperatureEventGenerator generator = new CpuTemperatureEventGenerator();
        generator.startSendingReadings();
        

    }
}
