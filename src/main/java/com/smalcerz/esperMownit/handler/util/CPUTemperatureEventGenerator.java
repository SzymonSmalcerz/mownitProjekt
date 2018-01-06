package com.smalcerz.esperMownit.handler.util;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import com.smalcerz.esperMownit.event.TemperatureEvent;
import com.smalcerz.esperMownit.handler.TemperatureEventHandler;

public class CPUTemperatureEventGenerator extends EventGenerator {


    /**
     * Measures cpu's temperatures and creates Temperature events and lets the implementation class handle them.
     */
	@Override
    public void startSendingReadings() {
		
		
		 final Components components = JSensors.get.components();

		    
		    
    	
    	final long noOfTemperatureEvents = 1000;
    	this.eventHandler = TemperatureEventHandler.getInstance();
        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());
                
                int count = 0;
                while (count < noOfTemperatureEvents) {
                	
                	int averageTemp = 0;
                	int numOfMeasures = 0;
                	List<Cpu> cpus = components.cpus;
                	if (cpus != null) {
        		        for (final Cpu cpu : cpus) {
        		            if (cpu.sensors != null) {
        		              
        		              //Print temperatures
        		              List<Temperature> temps = cpu.sensors.temperatures;
        		              for (final Temperature temp : temps) {
        		            	  if(temp.value < 120) {

            		                  numOfMeasures+=1;
            		                  averageTemp+=temp.value;
        		            	  }
        		              }
        		              
        		            }
        		        }
        		    }
                	
                	if(numOfMeasures != 0) {
                    	averageTemp = (int)averageTemp/numOfMeasures;
                	}
                    TemperatureEvent ve = new TemperatureEvent(averageTemp, new Date());
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
