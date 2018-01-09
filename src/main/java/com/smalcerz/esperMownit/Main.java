package com.smalcerz.esperMownit;


import org.bson.Document;

import com.smalcerz.esperMownit.frame.Display;
import com.smalcerz.esperMownit.handler.util.CPUUsageEventGenerator;
import com.smalcerz.esperMownit.handler.util.RandomTemperatureEventGenerator;
import com.smalcerz.esperMownit.handler.util.WeatherPressureEventGenerator;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



/**
 * Entry point for the Demo. Run this from your IDE, or from the command line using 'mvn exec:java'.
 */


public class Main {

    
    /**
     * Main method - start the Demo!
     */
    public static void main(String[] args) throws Exception {


    	@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
    	MongoDatabase database = mongoClient.getDatabase("testDB");
    	
    	@SuppressWarnings("unused")
		Display display = Display.getInstance();
    	MongoCollection<Document> collection = database.getCollection("logCPUsTEST");
    	
    	RandomTemperatureEventGenerator generator = new RandomTemperatureEventGenerator(collection);
        CPUUsageEventGenerator generatorCPUUsage = new CPUUsageEventGenerator(collection);
        WeatherPressureEventGenerator WheatherPressuregenerator = new WeatherPressureEventGenerator(collection);

        generator.startSendingReadings();
        generatorCPUUsage.startSendingReadings();
        WheatherPressuregenerator.startSendingReadings();
        

    }
}
