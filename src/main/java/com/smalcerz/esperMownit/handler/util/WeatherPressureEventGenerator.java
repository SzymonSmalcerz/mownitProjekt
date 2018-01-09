package com.smalcerz.esperMownit.handler.util;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.event.WheatherPressureEvent;
import com.smalcerz.esperMownit.handler.WheatherPressureHandler;
import com.smalcerz.esperMownit.helpers.RequestUrl;

public class WeatherPressureEventGenerator extends EventGenerator {

	
	
	public WeatherPressureEventGenerator(MongoCollection<Document> collection) {
		super(collection);
	}
    /**
     * Measures cpu's temperatures and creates Temperature events and lets the implementation class handle them.
     */
	@Override
    public void startSendingReadings() {
		
		this.running = true;

		    
    	this.eventHandler = new WheatherPressureHandler(this.collection);
        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();
        
        xrayExecutor.submit(new Runnable() {
            public void run() {

                int pressure = 1024;
                LOG.debug(getStartingMessage());
                while (running) {
                	
                	JSONObject data = RequestUrl.getStringObj("https://api.forecast.io/forecast/4a04d1c42fd9d32c97a2c291a32d5e2d/50.0646501,19.9449799");
            		
            		
            		pressure = (int) data.getJSONObject("currently").getDouble("pressure");
                    WheatherPressureEvent ve = new WheatherPressureEvent(pressure, new Date());
                    eventHandler.handle(ve);
                    try {
                        Thread.sleep(1000);
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
        sb.append("\n* STARTING - WHEATHER PRESSURE GENERATOR");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }

}
