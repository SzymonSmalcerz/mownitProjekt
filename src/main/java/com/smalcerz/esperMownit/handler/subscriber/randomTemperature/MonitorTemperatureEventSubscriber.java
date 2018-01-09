package com.smalcerz.esperMownit.handler.subscriber.randomTemperature;

import java.util.Date;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.handler.subscriber.MonitorEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class MonitorTemperatureEventSubscriber extends MonitorEventSubscriber {

  

    public MonitorTemperatureEventSubscriber(MongoCollection<Document> collection) {
		super(collection);
	}

	/**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return "select avg(temperature) as avg_val from TemperatureEvent.win:time_batch(5 sec)";
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    	System.out.println("TUTAJJJJJJJJ");

        // average temp over 10 secs 
        Double avg = (Double) eventMap.get("avg_val");
        
        String actualLog = "\n- [MONITOR] Average Temp = " + avg;
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append(actualLog);
        sb.append("\n---------------------------------");
        
        actualLog += ", TIME OF MEASURES: " + new Date().toString();
        this.saveLogToDatabase(actualLog);
    }
}
