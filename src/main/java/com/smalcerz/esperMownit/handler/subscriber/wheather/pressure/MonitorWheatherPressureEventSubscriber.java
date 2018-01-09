package com.smalcerz.esperMownit.handler.subscriber.wheather.pressure;

import java.util.Date;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.smalcerz.esperMownit.handler.subscriber.MonitorEventSubscriber;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */

public class MonitorWheatherPressureEventSubscriber extends MonitorEventSubscriber {

  

    public MonitorWheatherPressureEventSubscriber(MongoCollection<Document> collection) {
		super(collection);
	}

	/**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return "select avg(pressure) as avg_val from WheatherPressureEvent.win:time_batch(5 sec)";
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {

        // average temp over 10 secs 
        Double avg = (Double) eventMap.get("avg_val");
        
        String actualLog = "\n- [MONITOR]  WHEATHER PRESSURE avg = " + avg;
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append(actualLog);
        sb.append("\n---------------------------------");
        
        actualLog += ", TIME OF MEASURES: " + new Date().toString();
        this.saveLogToDatabase(actualLog);
    }
}
