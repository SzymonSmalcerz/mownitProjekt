package com.smalcerz.esperMownit.handler.util;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.TemperatureEvent;
import com.smalcerz.esperMownit.handler.TemperatureEventHandler;


/**
 * Just a simple class to create a number of Random TemperatureEvents and pass them off to the
 * TemperatureEventHandler.
 */

public class RandomTemperatureEventGenerator extends EventGenerator{

    
	
	public RandomTemperatureEventGenerator(MongoCollection<Document> collection) {
		super(collection);
	}
    /**
     * Creates simple random Temperature events and lets the implementation class handle them.
     */
	@Override
    public void startSendingReadings() {
    	
    	final long noOfTemperatureEvents = 1000;
    	this.eventHandler = new TemperatureEventHandler(this.collection);
        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());
                
                int count = 0;
                while (count < noOfTemperatureEvents) {
                    TemperatureEvent ve = new TemperatureEvent(new Random().nextInt(100), new Date());
                    eventHandler.handle(ve);
                    count++;
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
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT - TEMPERATURES ARE RANDOM SO MAY TAKE");
        sb.append("\n* A WHILE TO SEE WARNING AND CRITICAL EVENTS!");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
