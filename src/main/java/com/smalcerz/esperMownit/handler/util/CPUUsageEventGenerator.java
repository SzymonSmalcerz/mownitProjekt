package com.smalcerz.esperMownit.handler.util;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.UsageEvent;
import com.smalcerz.esperMownit.handler.CPUUsageEventHandler;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class CPUUsageEventGenerator extends EventGenerator {
	 
	
	
	
	public CPUUsageEventGenerator(MongoCollection<Document> collection) {
		super(collection);
		this.collection.insertOne(new Document("log", "inserted in constructor of eventgenerator"));
	}

	/**
     * Measures cpu's temperatures and creates Temperature events and lets the implementation class handle them.
     */
	@Override
    public void startSendingReadings() {
		
		this.running = true;
		
		 
    	this.eventHandler = new CPUUsageEventHandler(this.collection);
        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());

                SystemInfo si = new SystemInfo();
                HardwareAbstractionLayer hal = si.getHardware();
                CentralProcessor processor = hal.getProcessor();
               
               while(running) {
            	   UsageEvent ve = new UsageEvent((int) (processor.getSystemCpuLoadBetweenTicks() * 100), new Date());

//            	   UsageEvent ve = new UsageEvent((int) 50, new Date());
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
