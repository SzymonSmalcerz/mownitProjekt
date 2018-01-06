package com.smalcerz.esperMownit.handler.util;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.smalcerz.esperMownit.event.UsageEvent;
import com.smalcerz.esperMownit.handler.CPUUsageEventHandler;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class CPUUsageEventGenerator extends EventGenerator {

	/**
     * Measures cpu's temperatures and creates Temperature events and lets the implementation class handle them.
     */
	@Override
    public void startSendingReadings() {
		
		
		 
    	this.eventHandler = CPUUsageEventHandler.getInstance();
        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());

                SystemInfo si = new SystemInfo();
                HardwareAbstractionLayer hal = si.getHardware();
                CentralProcessor processor = hal.getProcessor();
               
               while(true) {
            	   UsageEvent ve = new UsageEvent((int) (processor.getSystemCpuLoadBetweenTicks() * 100), new Date());
                   eventHandler.handle(ve);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        LOG.error("Thread Interrupted", e);
                    }
                }
            }
        });
    }

    
    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING ");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }


}
